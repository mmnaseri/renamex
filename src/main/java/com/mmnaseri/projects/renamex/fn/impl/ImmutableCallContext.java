package com.mmnaseri.projects.renamex.fn.impl;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.FunctionRegistry;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 12:38 AM)
 */
public class ImmutableCallContext implements CallContext {

    private final FunctionRegistry functionRegistry;
    private final List<List<List<String>>> groups;
    private final List<Object> arguments;

    public ImmutableCallContext(FunctionRegistry functionRegistry, List<List<List<String>>> groups, List<Object> arguments) {
        this.functionRegistry = functionRegistry;
        this.groups = groups;
        this.arguments = arguments;
    }

    @Override
    public void expectArguments(int count) {
        if (arguments.size() < count) {
            throw new IllegalArgumentException("Expected " + count + " arguments");
        }
    }

    @Override
    public void expectArgumentType(int index, Class<?> type) {
        if (!type.isInstance(arguments.get(index))) {
            throw new IllegalArgumentException("Expected argument " + index + " to be of type " + type);
        }
    }

    @Override
    public <E> E getArgument(int index, Class<E> type) {
        return type.cast(arguments.get(index));
    }

    @Override
    public String getMapping(int fragment, int filter, int group) {
        return groups.get(fragment).get(filter).get(group);
    }

    @Override
    public int getArgumentCount() {
        return arguments.size();
    }

    @Override
    public List<List<List<String>>> getGroups() {
        return groups.stream().map(lists -> lists.stream().map(Collections::unmodifiableList).collect(Collectors.toList())).collect(Collectors.toList());
    }

    @Override
    public FunctionRegistry getFunctionRegistry() {
        return functionRegistry;
    }

}
