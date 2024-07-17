package com.example;

import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.To;
import org.apache.kafka.streams.processor.Processor;

public class NumberProcessor implements Processor<Void,String>{

    private ProcessorContext context;

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public void process(Void key, String value) {
        System.out.println("-> " + value);
        context.forward(key, value, To.all());
    }

    @Override
    public void close() {
    }
}
