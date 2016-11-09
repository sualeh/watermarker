
package us.fatehi.watermarker;


import static java.util.Objects.requireNonNull;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

public abstract class ImageWatermark
{

  private static final Logger logger = Logger.getGlobal();

  private final float alpha;
  private final WatermarkPosition position;

  public ImageWatermark(final float alpha, final WatermarkPosition position)
  {
    this.alpha = alpha;
    requireNonNull(position, "No watermark position provided");
    this.position = position;
  }

  /**
   * Mark the watermark on an image, and save the output file.
   */
  public BufferedImage markImage(final BufferedImage sourceImage,
                                 final BufferedImage watermarkImage)
  {
    Objects.requireNonNull(sourceImage, "No source image provided");
    Objects.requireNonNull(sourceImage, "No watermark image provided");
    
    final int srcWidth = sourceImage.getWidth();
    final int srcHeight = sourceImage.getHeight();
    final int watermarkWidth = watermarkImage.getWidth();
    final int watermarkHeight = watermarkImage.getHeight();

    final BufferedImage image = new BufferedImage(srcWidth,
                                                  srcHeight,
                                                  BufferedImage.TYPE_INT_RGB);
    final Graphics2D g = image.createGraphics();
    g.drawImage(sourceImage, 0, 0, srcWidth, srcHeight, null);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);
    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    final BufferedImage resizedWatermarkImage = Scalr
      .resize(watermarkImage,
              Method.ULTRA_QUALITY,
              Math.min(watermarkWidth, srcWidth),
              Math.min(watermarkHeight, srcHeight));
    int resizedWatermarkWidth = resizedWatermarkImage.getWidth();
    int resizedWatermarkHeight = resizedWatermarkImage.getHeight();

    g.drawImage(resizedWatermarkImage,
                position.getOffsetX(srcWidth, resizedWatermarkWidth),
                position.getOffsetY(srcHeight, resizedWatermarkHeight),
                resizedWatermarkWidth,
                resizedWatermarkHeight,
                null);

    g.dispose();

    return image;
  }

}
