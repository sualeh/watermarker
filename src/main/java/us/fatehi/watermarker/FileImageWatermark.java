/*
========================================================================
Watermarker
https://github.com/sualeh/watermarker
Copyright (c) 2016, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------
Watermarker is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

Watermarker and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.
The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html
The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/
========================================================================
*/

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
  private final ImageSize finalImageSize;

  public FileImageWatermark(final Path sourceImageFile,
                            final Path watermarkImageFile,
                            final Path outputImageFile,
                            final float alpha,
                            final WatermarkPosition position,
                            final ImageSize finalImageSize)
  {
    super(alpha, position);
    this.sourceImageFile = checkReadableFile(sourceImageFile).toAbsolutePath();
    this.watermarkImageFile = checkReadableFile(watermarkImageFile)
      .toAbsolutePath();
    this.outputImageFile = checkWritableFile(outputImageFile).toAbsolutePath();
    this.finalImageSize = finalImageSize;
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

      final BufferedImage image = markImage(sourceImage, watermarkImage, finalImageSize);

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
