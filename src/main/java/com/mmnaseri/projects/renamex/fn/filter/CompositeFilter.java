package com.mmnaseri.projects.renamex.fn.filter;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.Function;
import com.mmnaseri.projects.renamex.fn.FunctionCall;
import com.mmnaseri.projects.renamex.fn.impl.AbstractFilter;
import com.mmnaseri.projects.renamex.fn.impl.ImmutableCallContext;

import java.io.File;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 12:46 AM)
 */
public class CompositeFilter extends AbstractFilter {

    public static String NAME = "|";

    @Override
    public List<String> call(File input, CallContext context) {
        final int count = context.getArgumentCount();
        Object currentInput = input;
        for (int i = 0; i < count; i++) {
            final FunctionCall call = context.getArgument(i, FunctionCall.class);
            final Function function = context.getFunctionRegistry().get(call.getName());
            //noinspection unchecked
            currentInput = function.call(currentInput, new ImmutableCallContext(context.getFunctionRegistry(), context.getGroups(), call.getArguments()));
        }
        if (currentInput == null) {
            return null;
        } else {
            //noinspection unchecked
            return (List<String>) currentInput;
        }
    }

}
