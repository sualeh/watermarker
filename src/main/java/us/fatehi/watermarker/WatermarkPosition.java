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


public enum WatermarkPosition
{

 top_left
 {
   @Override
   public int getOffsetX(final int srcWidth, final int wmWidth)
   {
     return OFFSET_X;
   }

   @Override
   public int getOffsetY(final int srcHeight, final int wmHeight)
   {
     return OFFSET_Y;
   }
 },
 top_right
 {
   @Override
   public int getOffsetX(final int srcWidth, final int wmWidth)
   {
     return srcWidth - wmWidth - OFFSET_X;
   }

   @Override
   public int getOffsetY(final int srcHeight, final int wmHeight)
   {
     return OFFSET_Y;
   }
 },
 bottom_left
 {
   @Override
   public int getOffsetX(final int srcWidth, final int wmWidth)
   {
     return OFFSET_X;
   }

   @Override
   public int getOffsetY(final int srcHeight, final int wmHeight)
   {
     return srcHeight - wmHeight - OFFSET_Y;
   }
 },
 bottom_right
 {
   @Override
   public int getOffsetX(final int srcWidth, final int wmWidth)
   {
     return srcWidth - wmWidth - OFFSET_X;
   }

   @Override
   public int getOffsetY(final int srcHeight, final int wmHeight)
   {
     return srcHeight - wmHeight - OFFSET_Y;
   }
 },
 center
 {
   @Override
   public int getOffsetX(final int srcWidth, final int wmWidth)
   {
     return (srcWidth - wmWidth - OFFSET_X) / 2;
   }

   @Override
   public int getOffsetY(final int srcHeight, final int wmHeight)
   {
     return (srcHeight - wmHeight - OFFSET_Y) / 2;
   }
 };

  private static final byte OFFSET_X = 0, OFFSET_Y = 0;

  public abstract int getOffsetX(int srcWidth, int wmWidth);

  public abstract int getOffsetY(int srcHeight, int wmHeight);

}
