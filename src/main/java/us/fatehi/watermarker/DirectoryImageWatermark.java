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

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

public class DirectoryImageWatermark
{

  private static final Logger logger = Logger.getGlobal();

  private final FileImageWatermark imageWatermark;

  public DirectoryImageWatermark(final Path watermarkImageFile,
                                 final float alpha,
                                 final WatermarkPosition position)
  {
    checkReadableFile(watermarkImageFile.toAbsolutePath());
    imageWatermark = new FileImageWatermark(watermarkImageFile,
                                            alpha,
                                            position);

  }

  /**
   * Mark the watermark on an image, and save the output file.
   *
   * @throws IOException
   */
  public void markImage(final Path sourceDirectory,
                        final Path outputImageDirectory,
                        final ImageSize finalImageSize)
  {
    checkReadableDirectory(sourceDirectory);
    checkOrCreateWritableDirectory(outputImageDirectory);
    requireNonNull(finalImageSize, "No final image size provided");

    try (final DirectoryStream<Path> directoryStream = Files
      .newDirectoryStream(sourceDirectory);)
    {
      StreamSupport
        .stream(directoryStream.spliterator(), false).filter(path -> path
          .getFileName().toString().toLowerCase().endsWith(".jpg"))
        .forEach(path -> {
          logger.log(Level.FINE, path.toAbsolutePath().toString());
          final Path outputImageFile = outputImageDirectory
            .resolve(path.getFileName()).toAbsolutePath();
          imageWatermark.markImage(path, outputImageFile, finalImageSize);
        });
    }
    catch (final IOException e)
    {
      throw new IllegalArgumentException("Cannot find files in directory", e);
    }

  }

  private Path checkOrCreateWritableDirectory(final Path directory)
  {
    requireNonNull(directory, "No directory provided");
    if (Files.exists(directory))
    {
      if (!Files.isWritable(directory))
      {
        throw new IllegalArgumentException(String
          .format("Directory is not writable, %s", directory));
      }
    }
    else
    {
      try
      {
        Files.createDirectory(directory);
      }
      catch (final IOException e)
      {
        throw new IllegalArgumentException("Cannot create directory", e);
      }
    }
    return directory;
  }

  private Path checkReadableDirectory(final Path directory)
  {
    requireNonNull(directory, "No directory provided");
    if (!(Files.isReadable(directory) && Files.isDirectory(directory)))
    {
      throw new IllegalArgumentException(String
        .format("Directory is not readable, %s", directory));
    }
    return directory;
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

}
