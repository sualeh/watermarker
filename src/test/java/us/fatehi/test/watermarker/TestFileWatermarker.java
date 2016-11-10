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
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;

import us.fatehi.watermarker.ImageWatermark;
import us.fatehi.watermarker.WatermarkPosition;

public class TestFileWatermarker
{

  public boolean compareImages(final BufferedImage image1,
                               final BufferedImage image2)
  {
    boolean isSame = true;
    final Raster raster1 = image1.getData();
    final DataBuffer buffer1 = raster1.getDataBuffer();
    final int size1 = buffer1.getSize();
    final Raster raster2 = image2.getData();
    final DataBuffer buffer2 = raster2.getDataBuffer();
    final int size2 = buffer2.getSize();
    if (size1 == size2)
    {
      for (int i = 0; i < size1; i++)
      {
        if (buffer1.getElem(i) != buffer2.getElem(i))
        {
          isSame = false;
          break;
        }
      }
    }
    else
    {
      System.out.println(String.format("%d %d", size1, size2));
      isSame = false;
    }
    return isSame;
  }

  @Test
  public void watermark()
    throws IOException
  {
    final ClassLoader classLoader = getClass().getClassLoader();
    final URL sourceImageUrl = classLoader
      .getResource("transparensee_0001.jpg");
    final BufferedImage sourceImage = ImageIO.read(sourceImageUrl);
    final URL watermarkUrl = classLoader.getResource("watermark1.png");
    final BufferedImage watermarkImage = ImageIO.read(watermarkUrl);
    final URL expectedWatermarkedImageUrl = classLoader
      .getResource("final1.jpg");
    final BufferedImage expectedWatermarkedImage = ImageIO
      .read(expectedWatermarkedImageUrl);

    final ImageWatermark imageWatermark = new ImageWatermark(0.4f,
                                                             WatermarkPosition.top_left);
    final BufferedImage watermarkedImage = imageWatermark
      .markImage(sourceImage, watermarkImage, null);

    final boolean same = compareImages(expectedWatermarkedImage,
                                       watermarkedImage);
    if (!same)
    {

      final File actualImageFile = new File("final1.jpg");
      ImageIO.write(watermarkedImage, "jpg", actualImageFile);
      System.out.println(actualImageFile.getAbsolutePath());
    }
    Assert.assertTrue(same);

  }

}
