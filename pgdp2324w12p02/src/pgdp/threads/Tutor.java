package pgdp.threads;

public class Tutor implements Runnable {
	private Buffer in;
	private Buffer out;
	private int taskNumber;
	private Buffer finished;

	public Tutor(Buffer in, Buffer out, int taskNumber, Buffer finished) {
		this.in = in;
		this.out = out;
		this.taskNumber = taskNumber;
		this.finished = finished;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Exam current = in.consume();
				if (correctExam(current, taskNumber)) {
					finished.produce(current);
				} else {
					out.produce(current);
				}
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	private boolean correctExam(Exam exam, int exercise) {
		int currentPoints = exam.getPoints()[taskNumber - 1];
		if (currentPoints < 0) {
			// first correction
			exam.setPoints(taskNumber, CorrectionScheme.points(taskNumber,
					exam.getAnswer(taskNumber)));
			return false;
		} else {
			// second correction
			if (Math.random() < 0.1) {
				if (currentPoints == 0 || Math.random() < 0.5) {
					exam.updateCorrection(taskNumber, 1);
				} else {
					exam.updateCorrection(taskNumber, -1);
				}
			}
			return taskNumber == 8;
		}
	}
}