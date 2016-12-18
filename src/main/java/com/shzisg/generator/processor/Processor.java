package com.shzisg.generator.processor;

import com.shzisg.generator.output.DomainDefine;

public interface Processor {

    DomainDefine process(DomainDefine domainDefine);
}
