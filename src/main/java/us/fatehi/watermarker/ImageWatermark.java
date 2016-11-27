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

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

public class ImageWatermark
{

  private final float alpha;
  private final WatermarkPosition position;
  private final BufferedImage watermarkImage;

  public ImageWatermark(final BufferedImage watermarkImage,
                        final float alpha,
                        final WatermarkPosition position)
  {
    this.watermarkImage = requireNonNull(watermarkImage,
                                         "No watermark image provided");
    this.position = requireNonNull(position, "No watermark position provided");
    this.alpha = alpha;
  }

  /**
   * Mark the watermark on an image, and save the output file.
   */
  public BufferedImage markImage(final BufferedImage sourceImage,
                                 final ImageSize finalSize)
  {
    requireNonNull(sourceImage, "No source image provided");
    requireNonNull(sourceImage, "No watermark image provided");

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
    final int resizedWatermarkWidth = resizedWatermarkImage.getWidth();
    final int resizedWatermarkHeight = resizedWatermarkImage.getHeight();

    g.drawImage(resizedWatermarkImage,
                position.getOffsetX(srcWidth, resizedWatermarkWidth),
                position.getOffsetY(srcHeight, resizedWatermarkHeight),
                resizedWatermarkWidth,
                resizedWatermarkHeight,
                null);

    g.dispose();

    final BufferedImage resizedImage;
    if (!ImageSize.isSameAsOriginal(finalSize))
    {
      resizedImage = Scalr.resize(image,
                                  Method.ULTRA_QUALITY,
                                  Math.max(1, finalSize.getWidth()),
                                  Math.max(1, finalSize.getHeight()));
    }
    else
    {
      resizedImage = image;
    }

    return resizedImage;
  }

}
