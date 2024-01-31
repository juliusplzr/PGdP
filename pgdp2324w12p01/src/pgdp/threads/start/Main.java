package pgdp.threads.start;

public class Main {
	public static void main(String[] args) {
		BusinessPenguin peter = new BusinessPenguin("Peter");
		BusinessPenguin paul = new BusinessPenguin("Paul");

		peter.setPartner(paul);
		paul.setPartner(peter);

		Customer petersCustomer = new Customer(peter);
		Customer paulsCustomer = new Customer(paul);
		Thread petersCustomerThread = new Thread(petersCustomer);
		Thread paulsCustomerThread = new Thread(paulsCustomer);

		petersCustomerThread.start();
		paulsCustomerThread.start();

		try {
			petersCustomerThread.join();
			paulsCustomerThread.join();
		} catch (InterruptedException e) {
			System.out.println("Something went wrong. Interrupted!");
			return;
		}

		System.out.println("Peter hat " + peter.getBalance());
		System.out.println("Paul hat " + paul.getBalance());

		/*
		 * Man erhält statt den erwarteten 10000 pro Pinguin irgendwelche Werte,
		 * die kleiner sind. Grund ist eine Race-Condition. Beide
		 * Customer-Threads greifen gleichzeitig auf die Balance zu und
		 * überschreiben dabei manchmal die Additionen des anderen.
		 * 
		 * Lösung ist eine Synchonisation, die garantiert, dass nur ein Thread
		 * gleichzeitig eine Addition durchführen kann.
		 */
	}
}