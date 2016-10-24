package com.mmnaseri.projects.renamex.fn.mapper;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.impl.AbstractMapper;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 11:28 PM)
 */
public class GroupReflectiveMapper extends AbstractMapper {

    public static String NAME = "#";

    @Override
    public String call(String input, CallContext context) {
        context.expectArguments(3);
        context.expectArgumentType(0, String.class);
        context.expectArgumentType(1, String.class);
        context.expectArgumentType(2, String.class);
        final int fragment = Integer.parseInt(context.getArgument(0, String.class)) - 1;
        final int filter = Integer.parseInt(context.getArgument(1, String.class)) - 1;
        final int group = Integer.parseInt(context.getArgument(2, String.class));
        return context.getMapping(fragment, filter, group);
    }

}
