package pgdp.image;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.regex.Pattern;

public class GradleMain {

    public static void printHelp(){
        System.out.println(
                """
                Usage: <program> [options] input_file output_file new_width
                
                    -h, --help: show this help message
                    -m <file>, --mask <file>: set mask to this file
                """);
    }

    public static void main(String[] args) throws IOException {
        String inputFile=null,outputFile=null,maskFile=null,widthString=null;
        int index=0;
        var maskPattern=Pattern.compile("--mask=(.*)");
        var helpPattern=Pattern.compile("--help|-[a-zA-Z]*h[a-zA-Z]*\\s*\\z");
        var maskPatternSplit=Pattern.compile("(-m|--mask)\\s*\\z");
        var optionPattern=Pattern.compile("-.*");
        for(int i=0;i< args.length;i++){
            if (helpPattern.matcher(args[i]).matches()){
                printHelp();
                System.exit(0);
            }
            else {
                var maskMatcher=maskPattern.matcher(args[i]);
                if(maskMatcher.matches())maskFile= maskMatcher.group(1);
                else if (maskPatternSplit.matcher(args[i]).matches()&&args.length>i+1) {
                    maskFile=args[++i];
                }else if (optionPattern.matcher(args[i]).matches()){
                    printHelp();
                    System.exit(1);
                }else{
                    switch (index){
                        case 0->{inputFile=args[i];index++;}
                        case 1->{outputFile=args[i];index++;}
                        case 2->{widthString=args[i];index++;}
                        default -> {
                            printHelp();
                            System.exit(1);
                        }
                    }
                }
            }
            
        }

        if(inputFile==null||outputFile==null||widthString==null){
            printHelp();
            System.exit(1);
        }
        var inputDecoders= ImageIO.getImageReadersBySuffix(inputFile.substring(inputFile.lastIndexOf('.')+1));
        if (!inputDecoders.hasNext()){
            System.out.println("Unable to decode "+inputFile);
            System.exit(1);
        }
        var outputEncoders=ImageIO.getImageWritersBySuffix(outputFile.substring(outputFile.lastIndexOf('.')+1));
        if (!outputEncoders.hasNext()){
            System.out.println("Unable to encode "+outputFile);
            System.exit(1);
        }
        int newWidth=Integer.parseInt(widthString);

        SimpleMain.commonMain(inputDecoders, inputFile, outputEncoders, outputFile, maskFile, newWidth);
    }
}
