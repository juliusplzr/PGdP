package pgdp.threaduins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleSignal implements Signal {
	@Override
	public void await() {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
			@SuppressWarnings("unused")
			final String input = in.readLine();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
