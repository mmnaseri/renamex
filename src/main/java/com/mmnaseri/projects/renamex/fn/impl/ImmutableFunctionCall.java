package com.mmnaseri.projects.renamex.fn.impl;

import com.mmnaseri.projects.renamex.fn.FunctionCall;

import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 9:06 PM)
 */
public class ImmutableFunctionCall implements FunctionCall {

    private final String name;
    private final List<Object> arguments;

    public ImmutableFunctionCall(String name, List<Object> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Object> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return name + arguments;
    }
}
