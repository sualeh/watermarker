package us.fatehi.test;


import static java.util.Objects.requireNonNull;

import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.PixelGrabber;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Core idea from
 * http://stackoverflow.com/questions/16602807/programmatically-determine-if-2-images-look-the-same-using-java
 */
public class ImageUtils
{

  /**
   * Compares two images to see if they are the same based on a visual
   * pixel by pixel comparison.
   *
   * @param image1
   *        First image to compare
   * @param image2
   *        Second image to compare
   * @return <code>true</code> if they are equal, otherwise
   *         <code>false</code>.
   */
  public final static boolean compare(final BufferedImage image1,
                                      final BufferedImage image2)
  {
    return equals(getPixels(image1), getPixels(image2));
  }

  private static Logger logger = Logger.getGlobal();

  private static final boolean equals(final int[] data1, final int[] data2)
  {
    final int length = data1.length;
    if (length != data2.length)
    {
      logger.log(Level.INFO, "File lengths are different.");
      return false;
    }
    for (int i = 0; i < length; i++)
    {
      if (data1[i] != data2[i])
      {

        // If the alpha is 0 for both that means that the pixels are
        // 100% transparent and the color does not matter. Return false
        // if only 1 is 100% transparent.
        if ((((data1[i] >> 24) & 0xff) == 0)
            && (((data2[i] >> 24) & 0xff) == 0))
        {
          logger.log(Level.INFO,
                     "Both pixles at spot {} are different but 100% transparent.",
                     Integer.valueOf(i));
        }
        else
        {
          logger.log(Level.INFO,
                     "The pixel {} is different.",
                     Integer.valueOf(i));
          return false;
        }
      }
    }
    logger.log(Level.INFO, "Both groups of pixels are the same.");
    return true;
  }

  private static final int[] getPixels(final BufferedImage image)
  {
    requireNonNull(image);

    final int width = image.getWidth();
    final int height = image.getHeight();
    final int[] pixelData = new int[width * height];

    final Image pixelImg;
    if (image.getColorModel().getColorSpace() == ColorSpace
      .getInstance(ColorSpace.CS_sRGB))
    {
      pixelImg = image;
    }
    else
    {
      pixelImg = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                                    null).filter(image, null);
    }

    final PixelGrabber pg = new PixelGrabber(pixelImg,
                                             0,
                                             0,
                                             width,
                                             height,
                                             pixelData,
                                             0,
                                             width);

    try
    {
      if (!pg.grabPixels())
      {
        throw new RuntimeException("Could not grab pixels");
      }
    }
    catch (final InterruptedException ie)
    {
      throw new RuntimeException("Could not grab pixels", ie);
    }

    return pixelData;
  }

}
