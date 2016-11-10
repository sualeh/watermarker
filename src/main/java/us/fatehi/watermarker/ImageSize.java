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

import java.awt.Image;
import java.io.Serializable;

public class ImageSize
  implements Serializable
{

  private static final long serialVersionUID = 6982734028894632551L;

  private final int width;
  private final int height;

  public ImageSize()
  {
    this(0, 0);
  }

  public ImageSize(final Image image)
  {
    requireNonNull(image, "No image provided");
    width = image.getWidth(null);
    height = image.getHeight(null);
  }

  public ImageSize(final int width, final int height)
  {
    this.width = width;
    this.height = height;
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    final ImageSize other = (ImageSize) obj;
    if (height != other.height)
    {
      return false;
    }
    if (width != other.width)
    {
      return false;
    }
    return true;
  }

  public int getHeight()
  {
    return height;
  }

  public int getWidth()
  {
    return width;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + height;
    result = prime * result + width;
    return result;
  }

  @Override
  public String toString()
  {
    return "ImageSize [width=" + width + ", height=" + height + "]";
  }

}
