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

  private static final byte OFFSET_X = 10, OFFSET_Y = 10;

  public abstract int getOffsetX(int srcWidth, int wmWidth);

  public abstract int getOffsetY(int srcHeight, int wmHeight);

}
