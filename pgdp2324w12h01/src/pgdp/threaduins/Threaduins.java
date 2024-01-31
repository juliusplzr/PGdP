package pgdp.threaduins;

import java.io.PrintStream;

public final class Threaduins {

	static final String WORKAHOLIC_WORKING_MSG = "Leave me alone! I'm working!";
	static final String WORKAHOLIC_STOP_MSG = "No! Why would you interrupt me?!?";

	static final String STOP_MSG = "Waiting for Signal!";
	static final String STOPPED_MSG = "HeHe :D";

	static final String PROCRASTINATOR_PROCRASTINATING_MSG = "Ahhhh, I will do this later ...";
	static final String LUCKY_PROCRASTINATOR_WORKING_MSG = "OH SHIT! The deadline is coming! I'll have to start right now.";

	private static Signal signal = new ConsoleSignal();

	protected Threaduins() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This is a method you can use to set a custom Signal during testing.
	 * 
	 * @param s custom Signal implementation.
	 */
	protected static void setSignal(Signal s) {
		signal = s;
	}

	/**
	 * Returns a workaholic penguin that keeps working unless someone forcefully
	 * stops him.
	 * 
	 * @param s PrintStream the penguin uses to communicate.
	 * @return Thread of a workaholic penguin.
	 */
	public static Thread getWorkaholic(PrintStream s) {
		return new Thread() {
			@Override
			public void run() {
				while (!isInterrupted()) {
					s.println(WORKAHOLIC_WORKING_MSG);
				}
				s.println(WORKAHOLIC_STOP_MSG);
			}
		};
	}

	/**
	 * Method that starts a workaholic penguin. Once the specified signal is given
	 * the penguin is forced to stop working.
	 * 
	 * @param workaholic Thread of a workaholic penguin.
	 * @throws Exception
	 */
	public static void stopWorkaholic(Thread workaholic) {
		workaholic.start();
		try {
			System.out.println(STOP_MSG);
			signal.await();
			workaholic.interrupt();
			workaholic.join();
			System.out.println(STOPPED_MSG);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a procrastinating penguin that won't do anything until someone sends
	 * a reminder for the nearing deadline of the PGdP exercise.
	 * 
	 * @param s PrintStream the penguin uses to communicate.
	 * @return Thread of a procrastinating penguin.
	 */
	public static Thread getLuckyProcrastinator(PrintStream s) {
		return new Thread() {
			@Override
			public void run() {
				s.println(PROCRASTINATOR_PROCRASTINATING_MSG);
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						interrupt();
					}
				}
				s.println(LUCKY_PROCRASTINATOR_WORKING_MSG);
			}
		};
	}

	/**
	 * Method that starts a workaholic penguin. Once the specified signal is given
	 * the penguin will be reminded of the deadline.
	 * 
	 * @param procrastinator Thread of a procrastinating penguin.
	 */
	public static void stopProcrastinator(Thread procrastinator) {
		procrastinator.start();
		try {
			System.out.println(STOP_MSG);
			signal.await();
			synchronized (procrastinator) {
				procrastinator.notify();
			}
			procrastinator.join();
			System.out.println(STOPPED_MSG);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) {
		// set a custom Signal
		// in this case we use another ConsoleSignal
		Threaduins.setSignal(new ConsoleSignal());

		// workaholic example
		final Thread workaholic = getWorkaholic(System.out);
		stopWorkaholic(workaholic);

		// procrastinator example
		// final Thread luckyProc = getLuckyProcrastinator(System.out);
		// stopProcrastinator(luckyProc);
	}

}
