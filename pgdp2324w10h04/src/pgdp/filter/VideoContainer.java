package pgdp.filter;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class VideoContainer {

	private FrameProvider provider;

	private Stream<Frame> frameStream;

	/**
	 * Nutzt javacv um Videodatei darzustellen.
	 *
	 * @param filename Dateiname
	 * @throws FileNotFoundException
	 * @throws IllegalVideoFormatException
	 * @throws org.bytedeco.javacv.FFmpegFrameGrabber.Exception
	 */
	public VideoContainer(FrameProvider provider) throws FileNotFoundException, IllegalVideoFormatException {

		this.provider = provider;

		if (!provider.fileExists()) {
			throw new FileNotFoundException();
		}

		if (provider.getWidth() == 0 || provider.getHeight() == 0) {
			throw new IllegalVideoFormatException();
		}

		frameStream = StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(new FrameIterator(), Spliterator.ORDERED), false);
	}

	/**
	 * TODO: zu implementieren Appliziert Funktion auf den Frame Stream
	 *
	 * @param checkFunction
	 */
	public void applyFunc(Function<Frame, Frame> function) {
		this.frameStream = this.frameStream.map(function);
	}

	public void limit(long frames) {
		this.frameStream = this.frameStream.limit(frames);
	}

	public FrameProvider getProvider() {
		return provider;
	}

	public void setProvider(FrameProvider provider) {
		this.provider = provider;
	}

	public Stream<Frame> getFrameStream() {
		return frameStream;
	}

	public void write(FrameConsumer fc) throws org.bytedeco.javacv.FrameRecorder.Exception {
	    
		frameStream.forEach(t -> {
			try {
				fc.consume(t);
			} catch (org.bytedeco.javacv.FFmpegFrameRecorder.Exception e) {
				System.err.println("Issue writing frame. Frame skipped.");
			}
		});
		fc.close();
	}

	/**
	 * TODO: Zu implementieren Exception für invalide Videoformate. Kann message
	 * entgegen nehmen.
	 */
	public static class IllegalVideoFormatException extends Exception{

		private static final long serialVersionUID = 1337694201234L;
	}

	private class FrameIterator implements Iterator<Frame> {

		private Frame cur;
		private Frame next;

		FrameIterator() {
			try {
				next = provider.nextFrame();
			} catch (org.bytedeco.javacv.FFmpegFrameGrabber.Exception e) {
				next = null;
			}
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public Frame next() {

			if (next == null) {
				throw new NoSuchElementException();
			}
			
			cur = next;
			try {
				next = provider.nextFrame();
			} catch (org.bytedeco.javacv.FFmpegFrameGrabber.Exception e) {
				next = null;
			}
			return cur;
		}

	}
}
