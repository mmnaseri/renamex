package com.mmnaseri.projects.renamex.fn;

import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 11:12 PM)
 */
public interface CallContext {

    void expectArguments(int count);

    void expectArgumentType(int index, Class<?> type);

    <E> E getArgument(int index, Class<E> type);

    String getMapping(int fragment, int filter, int group);

    int getArgumentCount();

    List<List<List<String>>> getGroups();

    FunctionRegistry getFunctionRegistry();

}
