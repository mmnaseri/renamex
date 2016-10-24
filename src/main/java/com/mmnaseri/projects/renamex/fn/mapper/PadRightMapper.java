package com.mmnaseri.projects.renamex.fn.mapper;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.impl.AbstractMapper;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 12:08 AM)
 */
public class PadRightMapper extends AbstractMapper {

    @Override
    public String call(String input, CallContext context) {
        context.expectArguments(2);
        context.expectArgumentType(0, String.class);
        context.expectArgumentType(1, String.class);
        final int length = Integer.parseInt(context.getArgument(0, String.class));
        while (input.length() < length) {
            input = input + context.getArgument(1, String.class);
        }
        return input;
    }

}
