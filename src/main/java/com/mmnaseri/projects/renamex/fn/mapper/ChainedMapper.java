package com.mmnaseri.projects.renamex.fn.mapper;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.Function;
import com.mmnaseri.projects.renamex.fn.FunctionCall;
import com.mmnaseri.projects.renamex.fn.impl.AbstractMapper;
import com.mmnaseri.projects.renamex.fn.impl.ImmutableCallContext;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 1:58 PM)
 */
public class ChainedMapper extends AbstractMapper {

    public static final String NAME = "~~";

    @Override
    public String call(String input, CallContext context) {
        String result = input;
        for (int i = 0; i < context.getArgumentCount(); i++) {
            final FunctionCall call = context.getArgument(i, FunctionCall.class);
            final Function<?, ?> function = context.getFunctionRegistry().get(call.getName());
            if (function instanceof AbstractMapper) {
                AbstractMapper mapper = (AbstractMapper) function;
                result = mapper.call(result, new ImmutableCallContext(context.getFunctionRegistry(), context.getGroups(), call.getArguments()));
            } else {
                throw new IllegalArgumentException("Mapper " + i + " is not of the correct type");
            }
        }
        return result;
    }

}
