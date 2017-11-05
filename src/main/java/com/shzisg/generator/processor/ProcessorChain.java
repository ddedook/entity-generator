package com.shzisg.generator.processor;

import com.shzisg.generator.output.DomainDefine;

import java.util.ArrayList;
import java.util.List;

public class ProcessorChain implements Processor {

    private List<Processor> processors = new ArrayList<>();

    public ProcessorChain() {
        processors.add(new NormalProcessor());
        processors.add(new DatabaseProcessor());
        processors.add(new LombokProcessor());
        processors.add(new JsonProcessor());
        processors.add(new SwaggerProcessor());
    }

    @Override
    public DomainDefine process(DomainDefine define) {
        DomainDefine result = define;
        for (Processor processor : processors) {
            result = processor.process(result);
        }
        return result;
    }
}
