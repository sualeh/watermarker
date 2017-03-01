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
package us.fatehi.util;


import static java.util.Objects.requireNonNull;

import java.nio.file.Files;
import java.nio.file.Path;

public interface FileUtility
{

  public static Path checkReadableDirectory(final Path directory)
  {
    requireNonNull(directory, "No directory provided");
    if (!(Files.isReadable(directory) && Files.isDirectory(directory)))
    {
      throw new IllegalArgumentException(String
        .format("Directory is not readable, %s", directory));
    }
    return directory;
  }

  public static Path checkReadableFile(final Path file)
  {
    requireNonNull(file, "No file provided");
    if (!(Files.isReadable(file) && Files.isRegularFile(file)))
    {
      throw new IllegalArgumentException(String
        .format("File is not readable, %s", file));
    }
    return file;
  }

  public static Path checkWritableFile(final Path file)
  {
    requireNonNull(file, "No file provided");
    if (Files.exists(file) && !Files.isWritable(file))
    {
      throw new IllegalArgumentException(String
        .format("File is not writable, %s", file));
    }
    return file;
  }

}
