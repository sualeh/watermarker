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


import static java.util.Objects.requireNonNull;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.thymeleaf.util.StringUtils;

import us.fatehi.util.FileUtility;

public class TemplateProcessor
{

  private final String template;

  public TemplateProcessor(final String template)
  {
    this.template = requireNonNull(template, "No template provided");
  }

  private Iterable<Path> iterableFiles(final Path directory, final String glob)
    throws Exception
  {
    return Files.newDirectoryStream(directory, new FileFilter(glob));
  }

  public void process(final Path directory,
                      final String glob,
                      final Writer writer)
    throws Exception
  {
    if (StringUtils.isEmpty(template))
    {
      throw new Exception("No template provided");
    }
    FileUtility.checkReadableDirectory(directory);
    if (writer == null)
    {
      throw new Exception("No output writer provided");
    }

    final TemplateEngine engine = new TemplateEngine();

    TemplateResolver resolver;
    resolver = new ClassLoaderTemplateResolver();
    resolver.setTemplateMode("XHTML");
    resolver.setSuffix(".html");
    engine.setTemplateResolver(resolver);
    resolver = new FileTemplateResolver();
    resolver.setTemplateMode("XHTML");
    resolver.setSuffix(".html");
    engine.setTemplateResolver(resolver);

    final Context context = new Context();
    context.setVariable("directory", iterableFiles(directory, glob));
    engine.process(template, context, writer);
  }

}
