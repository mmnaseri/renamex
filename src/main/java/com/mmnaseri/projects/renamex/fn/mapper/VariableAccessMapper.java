package com.mmnaseri.projects.renamex.fn.mapper;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.FunctionCall;
import com.mmnaseri.projects.renamex.fn.impl.AbstractMapper;
import com.mmnaseri.projects.renamex.fn.impl.ImmutableCallContext;

import java.io.File;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 2:22 PM)
 */
public class VariableAccessMapper extends AbstractMapper {

    public static final String NAME = "$";

    @Override
    public String call(String input, CallContext context) {
        final FunctionCall call = context.getArgument(0, FunctionCall.class);
        final File file = context.getArgument(1, File.class);
        final CompositeMapper mapper = (CompositeMapper) context.getFunctionRegistry().get(call.getName());
        return mapper.call(file, new ImmutableCallContext(context.getFunctionRegistry(), context.getGroups(), call.getArguments()));
    }
}
