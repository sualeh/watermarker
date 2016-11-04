package us.fatehi.watermarker;


import java.nio.file.Paths;

public class Main
{

  public static void main(final String[] args)
  {
    final ImageWatermark imageWatermark = new ImageWatermark(Paths.get(args[0]),
                                                             Paths.get(args[1]),
                                                             Paths.get(args[2]),
                                                             0.4f,
                                                             WatermarkPosition.top_left);
    imageWatermark.markImage();
  }

}
