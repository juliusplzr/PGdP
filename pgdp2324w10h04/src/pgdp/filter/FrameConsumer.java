package pgdp.filter;

import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameRecorder.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;

public class FrameConsumer {

	private Java2DFrameConverter paintConverter = new Java2DFrameConverter();
	private FFmpegFrameRecorder recorder;

	private static final int STANDARD_BITRATE = 90000000;
	
	// Nur fürs testing, nicht benutzen.
	public FrameConsumer() {}
	
	public FrameConsumer(FrameProvider provider, String filename)
			throws FFmpegFrameRecorder.Exception {
		this(provider, filename, provider.getWidth(), provider.getHeight());
	}

	public FrameConsumer(FrameProvider provider, String filename, int width, int heigth)
			throws FFmpegFrameRecorder.Exception {

		recorder = new FFmpegFrameRecorder(filename, width, heigth, 0);
		recorder.setVideoBitrate(STANDARD_BITRATE);
		recorder.setFrameRate(Math.floor(provider.getFrameRate()));
		recorder.setVideoCodecName("libx264rgb");
		recorder.setVideoQuality(0);
		recorder.start();
	}

	public void consume(Frame frame) throws FFmpegFrameRecorder.Exception {
		recorder.record(paintConverter.convert(frame.getPixels()));
	}

	public void close() throws Exception {

		recorder.close();
		paintConverter.close();
	}
}
