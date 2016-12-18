package com.shzisg.generator.writer;

import com.shzisg.generator.output.DomainDefine;
import com.shzisg.generator.output.EntityDefine;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FreemarkerWriter {

    public void write(DomainDefine define) {
        Configuration cfg = new Configuration(new Version("2.3.23"));
        try {
            InputStream input = getClass().getResourceAsStream("/templates/entity.ftl");
            File file = createTempFile(input);
            cfg.setDirectoryForTemplateLoading(new File(file.getParent()));
            Template template = cfg.getTemplate(file.getName());
            for (EntityDefine entity : define.getEntities()) {
                StringWriter out = new StringWriter();
                template.process(entity, out);
                String packagePath = entity.getPackagePath().replaceAll("\\.", "/");
                String filePath = define.getRootPath() + "/src/main/java/" + packagePath + "/" + entity.getName() + ".java";
                generateFileContent(filePath, out.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("Generate entity file failed.", e);
        }
    }

    private static File createTempFile(InputStream inputStream) throws IOException {
        File file = File.createTempFile("tempfile", ".ftl");
        OutputStream fileOut = new FileOutputStream(file);
        int read;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            fileOut.write(bytes, 0, read);
        }
        file.deleteOnExit();
        return file;
    }

    public static void generateFileContent(String path, String data) throws RuntimeException {
        try {
            Files.createDirectories(Paths.get(path.substring(0, path.lastIndexOf("/"))));
            Files.write(Paths.get(path), data.getBytes("utf-8"), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Create file failed!", e);
        }
    }
}
