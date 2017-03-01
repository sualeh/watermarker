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
import static us.fatehi.util.FileUtility.checkReadableFile;
import static us.fatehi.util.FileUtility.checkWritableFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class FileImageWatermark
{

  private static final Logger logger = Logger.getGlobal();

  private final ImageWatermark imageWatermark;

  public FileImageWatermark(final Path watermarkImageFile,
                            final float alpha,
                            final WatermarkPosition position)
  {
    checkReadableFile(watermarkImageFile.toAbsolutePath());
    try
    {
      imageWatermark = new ImageWatermark(ImageIO
        .read(watermarkImageFile.toAbsolutePath().toFile()), alpha, position);
    }
    catch (final IOException e)
    {
      throw new IllegalArgumentException("Cannot read watermark image", e);
    }

  }

  /**
   * Mark the watermark on an image, and save the output file.
   */
  public void markImage(final Path sourceImageFile,
                        final Path outputImageFile,
                        final ImageSize finalImageSize)
  {
    checkReadableFile(sourceImageFile).toAbsolutePath();
    checkWritableFile(outputImageFile).toAbsolutePath();
    requireNonNull(finalImageSize, "No final image size provided");

    try
    {
      final BufferedImage sourceImage = ImageIO.read(sourceImageFile.toFile());

      final BufferedImage image = imageWatermark.markImage(sourceImage,
                                                           finalImageSize);

      ImageIO.write(image, "jpg", outputImageFile.toFile());

    }
    catch (final Exception e)
    {
      logger.log(Level.SEVERE, "Could not mark source, " + sourceImageFile, e);
    }
  }

}
