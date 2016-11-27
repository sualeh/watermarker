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
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;

import us.fatehi.test.ImageUtils;

public class TestImageUtils
{

  @Test
  public void compareImages()
    throws IOException
  {
    final ClassLoader classLoader = getClass().getClassLoader();
    final URL sourceImageUrl = classLoader
      .getResource("transparensee_0001.jpg");
    final BufferedImage sourceImage = ImageIO.read(sourceImageUrl);
    final URL expectedWatermarkedImageUrl = classLoader
      .getResource("final1.jpg");
    final BufferedImage expectedWatermarkedImage = ImageIO
      .read(expectedWatermarkedImageUrl);

    final boolean same = ImageUtils.compare(expectedWatermarkedImage,
                                            sourceImage);
    Assert.assertTrue(!same);

  }

}
