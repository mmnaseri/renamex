package com.mmnaseri.projects.renamex.fn.mapper;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.impl.AbstractMapper;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 12:34 AM)
 */
public class IdentityMapper extends AbstractMapper {

    public static String NAME = "=";

    @Override
    public String call(String input, CallContext context) {
        context.expectArguments(1);
        context.expectArgumentType(0, String.class);
        return context.getArgument(0, String.class);
    }
}
