package com.mmnaseri.projects.renamex.conf;

import com.mmnaseri.projects.renamex.fn.FunctionCall;

import java.util.List;
import java.util.Map;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 12:33 AM)
 */
public class CompiledConfiguration {

    private final boolean dryRun;
    private final String input;
    private final FunctionCall output;
    private final Map<String, List<FunctionCall>> filters;
    private final int depth;
    private final Map<String, FunctionCall> variables;

    public CompiledConfiguration(boolean dryRun, String input, FunctionCall output, Map<String, List<FunctionCall>> filters, Map<String, FunctionCall> variables) {
        this.dryRun = dryRun;
        this.input = input;
        this.output = output;
        this.filters = filters;
        this.depth = input.split("/").length;
        this.variables = variables;
    }

    public boolean isDryRun() {
        return dryRun;
    }

    public String getInput() {
        return input;
    }

    public FunctionCall getOutput() {
        return output;
    }

    public Map<String, List<FunctionCall>> getFilters() {
        return filters;
    }

    public int getDepth() {
        return depth;
    }

    public Map<String, FunctionCall> getVariables() {
        return variables;
    }

}
