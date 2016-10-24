package com.mmnaseri.projects.renamex.fn.impl;

import com.mmnaseri.projects.renamex.fn.FunctionCall;
import com.mmnaseri.projects.renamex.fn.FunctionParser;
import com.mmnaseri.projects.renamex.str.DefaultDocumentReader;
import com.mmnaseri.projects.renamex.str.DocumentReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 9:07 PM)
 */
public class DefaultFunctionParser implements FunctionParser {

    private final FunctionSnippetParser parser;

    public DefaultFunctionParser(FunctionSnippetParser parser) {
        this.parser = parser;
    }

    @Override
    public List<FunctionCall> parse(final String text) {
        final List<FunctionCall> calls = new ArrayList<>();
        final DocumentReader reader = new DefaultDocumentReader(text);
        while (reader.hasMore()) {
            final FunctionCall call = reader.parse(parser);
            calls.add(call);
        }
        return calls;
    }

}
