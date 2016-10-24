package com.mmnaseri.projects.renamex.fn.mapper;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.Function;
import com.mmnaseri.projects.renamex.fn.FunctionCall;
import com.mmnaseri.projects.renamex.fn.FunctionRegistry;
import com.mmnaseri.projects.renamex.fn.impl.AbstractFunction;
import com.mmnaseri.projects.renamex.fn.impl.AbstractMapper;
import com.mmnaseri.projects.renamex.fn.impl.ImmutableCallContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 12:34 AM)
 */
public class CompositeMapper extends AbstractFunction<File, String> {

    public static String NAME = "=>";

    public CompositeMapper() {
        super(File.class, String.class);
    }

    @Override
    public String call(File input, CallContext context) {
        final int count = context.getArgumentCount();
        final List<List<List<String>>> groups = context.getGroups();
        final StringBuilder builder = new StringBuilder();
        final FunctionRegistry registry = context.getFunctionRegistry();
        final String absolutePath = input.getAbsolutePath();
        //noinspection unchecked
        for (int i = 0; i < count; i++) {
            final FunctionCall call = context.getArgument(i, FunctionCall.class);
            final Function<?, ?> function = registry.get(call.getName());
            final String fragment;
            if (function instanceof AbstractMapper) {
                AbstractMapper mapper = (AbstractMapper) function;
                final List<Object> arguments = new ArrayList<>(call.getArguments());
                if (function instanceof VariableAccessMapper) {
                    arguments.add(input);
                }
                fragment = mapper.call(absolutePath, new ImmutableCallContext(registry, groups, arguments));
            } else {
                throw new IllegalStateException("Mapper " + i + " is not of the correct type");
            }
            builder.append(fragment);
        }
        return builder.toString();
    }

}
