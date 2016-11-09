
package us.fatehi.watermarker;


import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class FileImageWatermark
  extends ImageWatermark
{

  private static final Logger logger = Logger.getGlobal();

  private final Path sourceImageFile;
  private final Path watermarkImageFile;
  private final Path outputImageFile;

  public FileImageWatermark(final Path sourceImageFile,
                            final Path watermarkImageFile,
                            final Path outputImageFile,
                            final float alpha,
                            final WatermarkPosition position)
  {
    super(alpha, position);
    this.sourceImageFile = checkReadableFile(sourceImageFile).toAbsolutePath();
    this.watermarkImageFile = checkReadableFile(watermarkImageFile)
      .toAbsolutePath();
    this.outputImageFile = checkWritableFile(outputImageFile).toAbsolutePath();
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

      final BufferedImage image = markImage(sourceImage, watermarkImage);

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
