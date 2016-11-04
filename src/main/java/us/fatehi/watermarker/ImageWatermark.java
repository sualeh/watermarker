
package us.fatehi.watermarker;


import static java.util.Objects.requireNonNull;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

public class ImageWatermark
{

  private static final Logger logger = Logger.getGlobal();

  private final Path sourceImageFile;
  private final Path watermarkImageFile;
  private final Path outputImageFile;
  private final float alpha;
  private final WatermarkPosition position;

  public ImageWatermark(final Path sourceImageFile,
                        final Path watermarkImageFile,
                        final Path outputImageFile,
                        final float alpha,
                        final WatermarkPosition position)
  {

    this.sourceImageFile = checkReadableFile(sourceImageFile).toAbsolutePath();
    this.watermarkImageFile = checkReadableFile(watermarkImageFile)
      .toAbsolutePath();
    this.outputImageFile = checkWritableFile(outputImageFile).toAbsolutePath();
    this.alpha = alpha;
    requireNonNull(position, "No watermark position provided");
    this.position = position;
  }

  /**
   * Mark the watermark on an image, and save the output file.
   */
  public void markImage()
  {
    try
    {
      final BufferedImage sourceImage = ImageIO.read(sourceImageFile.toFile());
      final BufferedImage watermarkImage = ImageIO
        .read(watermarkImageFile.toFile());

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
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                alpha));
      final BufferedImage resizedWatermarkImage = Scalr
        .resize(watermarkImage,
                Method.ULTRA_QUALITY,
                Math.min(watermarkWidth, srcWidth),
                Math.min(watermarkWidth, srcHeight));
      g.drawImage(resizedWatermarkImage,
                  position.getOffsetX(srcWidth,
                                      resizedWatermarkImage.getWidth()),
                  position.getOffsetY(srcHeight,
                                      resizedWatermarkImage.getHeight()),
                  watermarkWidth,
                  watermarkHeight,
                  null);

      g.dispose();

      ImageIO.write(image, "jpg", outputImageFile.toFile());

    }
    catch (final Exception e)
    {
      logger.log(Level.SEVERE, "Could not mark source, " + sourceImageFile, e);
    }
  }

  private Path checkReadableFile(final Path imageFile)
  {
    requireNonNull(imageFile, "No image file provided");
    if (!(Files.isReadable(imageFile) && Files.isRegularFile(imageFile)))
    {
      throw new IllegalArgumentException(String
        .format("File is not readable, %s", imageFile));
    }
    return imageFile;
  }

  private Path checkWritableFile(final Path imageFile)
  {
    requireNonNull(imageFile, "No image file provided");
    if (Files.exists(imageFile) && !Files.isWritable(imageFile))
    {
      throw new IllegalArgumentException(String
        .format("File is not writable, %s", imageFile));
    }
    return imageFile;
  }

}
