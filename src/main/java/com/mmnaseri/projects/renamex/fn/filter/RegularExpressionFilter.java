package com.mmnaseri.projects.renamex.fn.filter;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.impl.AbstractFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 11:14 PM)
 */
public class RegularExpressionFilter extends AbstractFilter {

    public static String NAME = "~";

    @Override
    public List<String> call(File input, CallContext context) {
        context.expectArguments(1);
        context.expectArgumentType(0, Pattern.class);
        final Pattern pattern = context.getArgument(0, Pattern.class);
        final Matcher matcher = pattern.matcher(input.getName());
        if (!matcher.find()) {
            return null;
        }
        final List<String> list = new ArrayList<>(matcher.groupCount());
        for (int i = 0; i <= matcher.groupCount(); i++) {
            list.add(matcher.group(i));
        }
        return list;
    }

}
