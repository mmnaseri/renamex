package com.mmnaseri.projects.renamex.fn;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 11:06 PM)
 */
public interface FunctionRegistry {

    void register(String name, Function<?, ?> function);

    Function<?, ?> get(String name);

}
