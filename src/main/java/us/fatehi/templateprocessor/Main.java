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


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.beryx.textio.TextIO;
import org.beryx.textio.system.SystemTextTerminal;

public class Main
{

  public static void main(final String[] args)
    throws IOException, Exception
  {
    final TextIO textIO = new TextIO(new SystemTextTerminal());

    final String sourceImageDirectoryString = textIO.newStringInputReader()
      .read("Source directory");
    final Path sourceImageDirectory = Paths.get(sourceImageDirectoryString);

    final String globString = textIO.newStringInputReader()
      .read("Glob pattern");
    final String glob = globString;

    final String templateString = textIO.newStringInputReader()
      .read("Thymeleaf template");
    final String template = templateString;

    final String outputFileString = textIO.newStringInputReader()
      .read("Output file");
    final Path outputFile = Paths.get(outputFileString);

    textIO.dispose();

    final TemplateProcessor templateProcessor = new TemplateProcessor(template);
    templateProcessor.process(sourceImageDirectory,
                              glob,
                              new FileWriter(outputFile.toFile()));
  }

}
