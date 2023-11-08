package pgdp.image;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

public class SimpleMain {

    public static void main(String[] args) throws IOException {
        String inputFile = "example.png";
        String outputFile = "out.png";
        String maskFile = "mask.png";
        int newWidth = 875;

        var inputDecoders = ImageIO.getImageReadersBySuffix(inputFile.substring(inputFile.lastIndexOf('.')+1));
        var outputEncoders=ImageIO.getImageWritersBySuffix(outputFile.substring(outputFile.lastIndexOf('.')+1));

        commonMain(inputDecoders, inputFile, outputEncoders, outputFile, maskFile, newWidth);
    }

    public static void commonMain(Iterator<ImageReader> inputDecoders, String inputFile, Iterator<ImageWriter> outputEncoders, String outputFile, String maskFile, int newWidth) throws IOException {
        var inputDecoder=inputDecoders.next();
        inputDecoder.setInput(new FileImageInputStream(new File(inputFile)));
        var inputImage=inputDecoder.read(0);
        var input=inputImage.getRGB(0,0,inputImage.getWidth(),inputImage.getHeight(),null,0,inputImage.getWidth());

        var outputEncoder=outputEncoders.next();
        var outputF=new File(outputFile);
        if(!outputF.isFile())Files.createFile(outputF.toPath());
        outputEncoder.setOutput(new FileImageOutputStream(outputF));

        int[] mask = null;
        if(maskFile == null){
            mask=new int[input.length];
            Arrays.fill(mask,-1);
        }else{
            var maskDecoders=ImageIO.getImageReadersBySuffix(maskFile.substring(maskFile.lastIndexOf('.')+1));
            if(!maskDecoders.hasNext()){
                System.out.println("Unable to decode "+maskFile);
                System.exit(1);
            }
            var maskDecoder=maskDecoders.next();
            maskDecoder.setInput(new FileImageInputStream(new File(maskFile)));
            var maskImage=maskDecoder.read(0);
            if(maskImage.getWidth()!=inputImage.getWidth()||maskImage.getHeight()!=inputImage.getHeight()){
                System.out.println("Mismatched image size between mask and input image");
                System.exit(1);
            }
            mask=maskImage.getRGB(0,0,maskImage.getWidth(),maskImage.getHeight(),null,0,maskImage.getWidth());
        }
        var output=new SeamCarving().shrink(input,mask,inputImage.getWidth(),inputImage.getHeight(),newWidth);
        var outputImage=new BufferedImage(newWidth,inputImage.getHeight(),BufferedImage.TYPE_INT_RGB);
        outputImage.setRGB(0,0,newWidth,inputImage.getHeight(),output,0,newWidth);
        outputEncoder.write(outputImage);
    }
}
