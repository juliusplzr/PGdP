package pgdp.threads.start;

public class Customer implements Runnable {
	private BusinessPenguin salespenguin;

	public Customer(BusinessPenguin salesman) {
		this.salespenguin = salesman;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5000; i++) {
			salespenguin.sellFish(2);
		}
	}
}