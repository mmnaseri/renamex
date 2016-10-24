package com.mmnaseri.projects.renamex.conf;

import com.mmnaseri.projects.renamex.fn.FunctionCall;
import com.mmnaseri.projects.renamex.fn.FunctionParser;
import com.mmnaseri.projects.renamex.fn.OutputMappingParser;
import com.mmnaseri.projects.renamex.fn.filter.CompositeFilter;
import com.mmnaseri.projects.renamex.fn.impl.DefaultFunctionParser;
import com.mmnaseri.projects.renamex.fn.impl.DefaultOutputMappingParser;
import com.mmnaseri.projects.renamex.fn.impl.FunctionSnippetParser;
import com.mmnaseri.projects.renamex.fn.impl.ImmutableFunctionCall;
import com.mmnaseri.projects.renamex.fn.mapper.CompositeMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/22/16, 12:43 PM)
 */
public class Configuration {

    private Boolean dryrun = false;
    private String in = null;
    private Map<String, List<String>> filters = null;
    private String out = null;
    private Map<String, String> vars = new HashMap<>();

    public Boolean getDryrun() {
        return dryrun;
    }

    public void setDryrun(Boolean dryrun) {
        this.dryrun = dryrun;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public Map<String, List<String>> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, List<String>> filters) {
        this.filters = filters;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public Map<String, String> getVars() {
        return vars;
    }

    public void setVars(Map<String, String> vars) {
        this.vars = vars;
    }

    @Override
    public String toString() {
        return (dryrun ? "[dry] " : "") + in + " --> " + out;
    }

    public CompiledConfiguration compile() {
        final FunctionSnippetParser parser = new FunctionSnippetParser();
        final Map<String, FunctionCall> variables = getVariables(parser);
        return new CompiledConfiguration(dryrun, checkInput(), getOutput(parser, variables), getFileFilters(parser), variables);
    }

    private Map<String, FunctionCall> getVariables(FunctionSnippetParser parser) {
        final Map<String, FunctionCall> variables = new HashMap<>();
        final OutputMappingParser mappingParser = new DefaultOutputMappingParser(parser, variables);
        for (String name : vars.keySet()) {
            final List<FunctionCall> calls = mappingParser.parse(vars.get(name));
            final List<Object> arguments = new ArrayList<>(calls.stream().map(call -> (Object) call).collect(Collectors.toList()));
            variables.put(name, new ImmutableFunctionCall(CompositeMapper.NAME, arguments));
        }
        return variables;
    }

    private String checkInput() {
        String input = in.replaceAll("(^/|/$)", "").replaceAll("/+", "/");
        int index = 1;
        final String[] fragments = input.split("/");
        for (int i = 0; i < fragments.length; i++) {
            if (!fragments[i].equals("$" + index)) {
                throw new IllegalArgumentException("Expected fragment name to be $" + i + " but was " + fragments[i]);
            }
            index ++;
        }
        return input;
    }

    private ImmutableFunctionCall getOutput(FunctionSnippetParser parser, Map<String, FunctionCall> variables) {
        final OutputMappingParser mappingParser = new DefaultOutputMappingParser(parser, variables);
        final List<FunctionCall> list = mappingParser.parse(out);
        final List<Object> arguments = new ArrayList<>(list.stream().map(functionCall -> (Object) functionCall).collect(Collectors.toList()));
        return new ImmutableFunctionCall(CompositeMapper.NAME, arguments);
    }

    private Map<String, List<FunctionCall>> getFileFilters(FunctionSnippetParser parser) {
        final FunctionParser functionParser = new DefaultFunctionParser(parser);
        int fragments = in.split("/").length;
        final Map<String, List<FunctionCall>> filters = new HashMap<>();
        for (String key : this.filters.keySet()) {
            if (!key.matches("\\$\\d+")) {
                throw new IllegalArgumentException("Unknown fragment: " + key);
            }
            final int index = Integer.parseInt(key.substring(1));
            if (index < 1 || index > fragments) {
                throw new IllegalArgumentException("Unknown fragment: " + key);
            }
            final List<FunctionCall> calls = new ArrayList<>();
            final List<String> expressions = this.filters.get(key);
            for (String expression : expressions) {
                final List<FunctionCall> callList = functionParser.parse("." + expression);
                final FunctionCall composite = new ImmutableFunctionCall(CompositeFilter.NAME, callList.stream().map(functionCall -> (Object) functionCall).collect(Collectors.toList()));
                calls.add(composite);
            }
            filters.put(key, calls);
        }
        return filters;
    }

}
