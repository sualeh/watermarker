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
package us.fatehi.templateprocessor;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

import org.thymeleaf.util.StringUtils;

public final class FileFilter
  implements DirectoryStream.Filter<Path>
{
  private final PathMatcher matcher;

  public FileFilter(final String glob)
    throws Exception
  {
    if (StringUtils.isEmpty(glob))
    {
      throw new Exception("No glob pattern provided");
    }
    matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
  }

  @Override
  public boolean accept(final Path filename)
    throws IOException
  {
    return matcher.matches(filename);
  }

}
