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


import java.nio.file.Path;
import java.nio.file.Paths;

public class Main
{

  public static void main(final String[] args)
  {
    final Path watermarkImageFile = Paths.get(args[0]);
    final Path sourceImageDirectory = Paths.get(args[1]);
    final Path outputImageDirectory = Paths.get(args[2]);
    final DirectoryImageWatermark imageWatermark = new DirectoryImageWatermark(watermarkImageFile,
                                                                               0.4f,
                                                                               WatermarkPosition.top_left);
    imageWatermark.markImage(sourceImageDirectory,
                             outputImageDirectory,
                             new ImageSize(800, 600));
  }

}
