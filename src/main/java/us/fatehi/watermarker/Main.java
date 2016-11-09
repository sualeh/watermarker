package us.fatehi.watermarker;


import java.nio.file.Path;
import java.nio.file.Paths;

public class Main
{

  public static void main(final String[] args)
  {
    Path sourceImageFile = Paths.get(args[0]);
    Path watermarkImageFile = Paths.get(args[1]);
    Path outputImageFile = Paths.get(args[2]);
    final FileImageWatermark imageWatermark = new FileImageWatermark(sourceImageFile,
                                                                     watermarkImageFile,
                                                                     outputImageFile,
                                                                     0.4f,
                                                                     WatermarkPosition.top_left);
    imageWatermark.markImage();
  }

}
