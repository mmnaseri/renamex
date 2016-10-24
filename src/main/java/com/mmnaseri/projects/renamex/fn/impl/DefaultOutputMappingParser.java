package com.mmnaseri.projects.renamex.fn.impl;

import com.mmnaseri.projects.renamex.fn.FunctionCall;
import com.mmnaseri.projects.renamex.fn.OutputMappingParser;
import com.mmnaseri.projects.renamex.fn.mapper.ChainedMapper;
import com.mmnaseri.projects.renamex.fn.mapper.IdentityMapper;
import com.mmnaseri.projects.renamex.fn.mapper.VariableAccessMapper;
import com.mmnaseri.projects.renamex.str.DefaultDocumentReader;
import com.mmnaseri.projects.renamex.str.DocumentReader;
import com.mmnaseri.projects.renamex.str.IdentifierTokenReader;
import com.mmnaseri.projects.renamex.str.SimpleToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 1:37 AM)
 */
public class DefaultOutputMappingParser implements OutputMappingParser {

    private final FunctionSnippetParser parser;
    private final Map<String, FunctionCall> variables;

    public DefaultOutputMappingParser(FunctionSnippetParser parser, Map<String, FunctionCall> variables) {
        this.parser = parser;
        this.variables = variables;
    }

    @Override
    public List<FunctionCall> parse(String text) {
        final DocumentReader reader = new DefaultDocumentReader(text);
        final List<FunctionCall> calls = new ArrayList<>();
        String fragment = "";
        while (reader.hasMore()) {
            if (reader.has("\\$\\d+\\.\\d+\\.\\d+")) {
                if (!fragment.isEmpty()) {
                    calls.add(new ImmutableFunctionCall(IdentityMapper.NAME, Collections.singletonList(fragment)));
                    fragment = "";
                }
                final List<Object> mappers = new ArrayList<>();
                mappers.add(reader.parse(parser));
                while (reader.has("\\s*\\.\\s*")) {
                    mappers.add(reader.parse(parser));
                }
                calls.add(new ImmutableFunctionCall(ChainedMapper.NAME, mappers));
            } else if (reader.has("\\\\.")) {
                final String value = reader.read(new SimpleToken(1, 2));
                fragment += value;
            } else {
                if (reader.has("\\$\\{")){
                    reader.read("\\$\\{", false);
                    reader.skip(Pattern.compile("\\s*"));
                    final String variable = reader.read(reader.expectToken(new IdentifierTokenReader()));
                    reader.expect("\\}", true);
                    if (!fragment.isEmpty()) {
                        calls.add(new ImmutableFunctionCall(IdentityMapper.NAME, Collections.singletonList(fragment)));
                        fragment = "";
                    }
                    if (!variables.containsKey(variable)) {
                        throw new IllegalArgumentException("Unknown variable: " + variable);
                    }
                    calls.add(new ImmutableFunctionCall(VariableAccessMapper.NAME, Collections.singletonList(variables.get(variable))));
                } else {
                    fragment += reader.read(".", false);
                }
            }
        }
        if (!fragment.isEmpty()) {
            calls.add(new ImmutableFunctionCall(IdentityMapper.NAME, Collections.singletonList(fragment)));
        }
        return calls;
    }

}
