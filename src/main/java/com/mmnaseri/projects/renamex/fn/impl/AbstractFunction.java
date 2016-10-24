package com.mmnaseri.projects.renamex.fn.impl;

import com.mmnaseri.projects.renamex.fn.Function;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 11:09 PM)
 */
public abstract class AbstractFunction<I, O> implements Function<I, O> {

    private final Class<I> inputType;
    private final Class<O> outputType;

    public AbstractFunction(Class<I> inputType, Class<O> outputType) {
        this.inputType = inputType;
        this.outputType = outputType;
    }

    @Override
    public Class<I> getInputType() {
        return inputType;
    }

    @Override
    public Class<O> getOutputType() {
        return outputType;
    }

}