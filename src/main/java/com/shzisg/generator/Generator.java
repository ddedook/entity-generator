package com.shzisg.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shzisg.generator.config.DomainConfig;
import com.shzisg.generator.config.GenConfig;
import com.shzisg.generator.output.DomainDefine;
import com.shzisg.generator.output.EntityDefine;
import com.shzisg.generator.processor.ProcessorChain;
import com.shzisg.generator.writer.FreemarkerWriter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @goal generate
 * @phase process-sources
 */
public class Generator extends AbstractMojo {

    /**
     * @parameter expression="${project.basedir}"
     * @required
     * @readonly
     */
    private File baseDir;

    /**
     * @parameter
     * @required
     */
    private String configFile;

    /**
     * @parameter
     */
    private String typeMappingFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        File file = new File(baseDir.getPath() + "/" + configFile);
        if (!file.exists()) {
            throw new RuntimeException("File " + configFile + " doesn't exist.");
        }
        String content;
        try {
            content = FileUtils.fileRead(file, "utf-8");
        } catch (IOException e) {
            throw new RuntimeException("Read file " + configFile + " failed", e);
        }

        ObjectMapper mapper = new ObjectMapper();
        GenConfig genConfig;
        try {
            genConfig = mapper.readValue(content, GenConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Parse config file failed.", e);
        }
        DatabaseReader databaseReader= new DatabaseReader();
        TypeMapper typeMapper = new TypeMapper();
        if (typeMappingFile != null && typeMappingFile.isEmpty()) {
            File typeFile = new File(baseDir.getPath() + "/" + typeMappingFile);
            if (!file.exists()) {
                throw new RuntimeException("File " + typeMappingFile + " doesn't exist.");
            }
            typeMapper.extend(typeFile);
        }
        databaseReader.setMapper(typeMapper);
        ProcessorChain processorChain = new ProcessorChain();
        FreemarkerWriter writer = new FreemarkerWriter();
        for (DomainConfig domainConfig : genConfig.getDomains()) {
            List<EntityDefine> entities = databaseReader.readFrom(domainConfig);
            DomainDefine define = new DomainDefine();
            define.setJackson(genConfig.isJackson());
            define.setLombok(genConfig.isLombok());
            define.setSwagger(genConfig.isSwagger());
            define.setRootPath(baseDir.getPath());
            define.setEntities(entities);
            define = processorChain.process(define);
            writer.write(define);
        }
    }
}
