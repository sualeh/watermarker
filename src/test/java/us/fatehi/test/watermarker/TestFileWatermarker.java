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

package us.fatehi.test.watermarker;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import us.fatehi.test.ImageUtils;
import us.fatehi.watermarker.ImageWatermark;
import us.fatehi.watermarker.WatermarkPosition;

public class TestFileWatermarker
{

  @Ignore
  @Test
  public void watermark()
    throws IOException
  {
    final BufferedImage sourceImage = loadImageFromResource("transparensee_0001.jpg");
    final BufferedImage watermarkImage = loadImageFromResource("watermark1.png");
    final BufferedImage expectedWatermarkedImage = loadImageFromResource("final1.jpg");

    // Watermark the photo
    final ImageWatermark imageWatermark = new ImageWatermark(watermarkImage,
                                                             0.4f,
                                                             WatermarkPosition.top_left);
    final BufferedImage watermarkedImage = reencodeImage(imageWatermark
      .markImage(sourceImage, null));

    final boolean same = ImageUtils.compare(expectedWatermarkedImage,
                                            watermarkedImage);
    if (!same)
    {
      final File actualImageFile = new File("final1.jpg");
      ImageIO.write(watermarkedImage, "jpg", actualImageFile);
      System.out.println(actualImageFile.getAbsolutePath());
    }
    Assert.assertTrue(same);

  }

  private final BufferedImage loadImageFromResource(final String resource)
    throws IOException
  {
    final ClassLoader classLoader = getClass().getClassLoader();
    final URL imageUrl = classLoader.getResource(resource);
    final BufferedImage image = ImageIO.read(imageUrl);
    return image;
  }

  private BufferedImage reencodeImage(final BufferedImage image)
    throws IOException
  {
    final File tempImageFile = Files.createTempFile("final", ".jpg")
      .toAbsolutePath().toFile();
    ImageIO.write(image, "jpg", tempImageFile);
    return ImageIO.read(tempImageFile);
  }

}
