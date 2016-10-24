package com.mmnaseri.projects.renamex.fn.mapper;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.impl.AbstractMapper;

import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 1:50 PM)
 */
public class ReplaceMapper extends AbstractMapper {

    @Override
    public String call(String input, CallContext context) {
        context.expectArgumentType(0, String.class);
        context.expectArgumentType(1, String.class);
        final int options;
        final int count;
        if (context.getArgumentCount() > 2) {
            context.expectArgumentType(2, String.class);
            options = Integer.parseInt(context.getArgument(2, String.class));
        } else {
            options = 0;
        }
        if (context.getArgumentCount() > 3) {
            context.expectArgumentType(3, String.class);
            count = Integer.parseInt(context.getArgument(3, String.class));
        } else {
            count = 0;
        }
        final String pattern = context.getArgument(0, String.class);
        final Pattern compiled = Pattern.compile(pattern, options);
        final String replacement = context.getArgument(1, String.class);
        if (count == 0) {
            return compiled.matcher(input).replaceAll(replacement);
        } else {
            String text = input;
            for (int i = 0; i < count; i++) {
                text = compiled.matcher(text).replaceFirst(replacement);
            }
            return text;
        }
    }

}
