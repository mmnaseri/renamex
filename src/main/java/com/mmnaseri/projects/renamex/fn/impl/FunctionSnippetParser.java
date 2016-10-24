package com.mmnaseri.projects.renamex.fn.impl;

import com.mmnaseri.projects.renamex.fn.FunctionCall;
import com.mmnaseri.projects.renamex.fn.filter.RegularExpressionFilter;
import com.mmnaseri.projects.renamex.str.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 1:37 AM)
 */
public class FunctionSnippetParser implements SnippetParser<FunctionCall> {

    @Override
    public FunctionCall parse(DocumentReader reader) {
        final String group = reader.read("\\$\\d+\\s*\\.\\s*\\d+\\s*\\.\\s*\\d+", true);
        if (group != null) {
            return new ImmutableFunctionCall("#", Arrays.asList(group.substring(1).replaceAll("\\s+", "").split("\\.")));
        }
        reader.expect("\\.", true);
        final Token regex = reader.nextToken(new ContainedTokenReader("/", "/", null));
        if (regex != null) {
            final String pattern = reader.read(regex).replaceAll("\\\\(.)", "$1");
            int options = 0;
            while (reader.hasMore() && !reader.has("\\.")) {
                final String option = reader.expect("[msi]", false);
                switch (option) {
                    case "m":
                        options |= Pattern.MULTILINE;
                        break;
                    case "s":
                        options |= Pattern.DOTALL;
                        break;
                    case "i":
                        options |= Pattern.CASE_INSENSITIVE;
                        break;
                }
            }
            return new ImmutableFunctionCall(RegularExpressionFilter.NAME, Collections.singletonList(Pattern.compile("^" + pattern + "$", options)));
        }
        final String name = reader.read(reader.expectToken(new IdentifierTokenReader()));
        final List<Object> arguments = new ArrayList<>();
        if (reader.has("\\(")) {
            List<String> texts = reader.parse(new ListParser("\\(", "\\)", "'\"`"));
            arguments.addAll(texts.stream().map(text -> text.replaceAll("\\\\(.)", "$1")).collect(Collectors.toList()));
        }
        return new ImmutableFunctionCall(name, arguments);
    }

}
