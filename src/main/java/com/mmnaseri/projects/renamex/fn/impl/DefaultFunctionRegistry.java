package com.mmnaseri.projects.renamex.fn.impl;

import com.mmnaseri.projects.renamex.fn.Function;
import com.mmnaseri.projects.renamex.fn.FunctionRegistry;
import com.mmnaseri.projects.renamex.fn.filter.*;
import com.mmnaseri.projects.renamex.fn.mapper.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 11:07 PM)
 */
public class DefaultFunctionRegistry implements FunctionRegistry {

    private final Map<String, Function<?, ?>> functions;

    public DefaultFunctionRegistry() {
        functions = new HashMap<>();
        register(RegularExpressionFilter.NAME, new RegularExpressionFilter());
        register("isFile", new IsFileFilter());
        register("isDir", new IsDirectoryFilter());
        register("isHidden", new IsHiddenFilter());
        register(NegatingFilter.NAME, new NegatingFilter());
        register(GroupReflectiveMapper.NAME, new GroupReflectiveMapper());
        register("lowerCase", new LowerCaseMapper());
        register("upperCase", new UpperCaseMapper());
        register("int", new NumberMapper());
        register("padLeft", new PadLeftMapper());
        register("padRight", new PadRightMapper());
        register(IdentityMapper.NAME, new IdentityMapper());
        register(CompositeMapper.NAME, new CompositeMapper());
        register(CompositeFilter.NAME, new CompositeFilter());
        register("capitalize", new CapitalizeMapper());
        register("header", new HeaderMapper());
        register("replace", new ReplaceMapper());
        register(ChainedMapper.NAME, new ChainedMapper());
        register(VariableAccessMapper.NAME, new VariableAccessMapper());
    }

    @Override
    public void register(String name, Function<?, ?> function) {
        functions.put(name, function);
    }

    @Override
    public Function<?, ?> get(String name) {
        if (!functions.containsKey(name)) {
            throw new IllegalArgumentException("No such function: " + name);
        }
        return functions.get(name);
    }

}
