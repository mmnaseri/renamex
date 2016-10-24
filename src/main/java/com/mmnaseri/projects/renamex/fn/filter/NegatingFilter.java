package com.mmnaseri.projects.renamex.fn.filter;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.impl.AbstractFunction;

import java.util.Collections;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 11:31 PM)
 */
public class NegatingFilter extends AbstractFunction<List, List> {

    public static String NAME = "not";

    public NegatingFilter() {
        super(List.class, List.class);
    }

    @Override
    public List<String> call(List input, CallContext context) {
        return input == null ? Collections.emptyList() : null;
    }

}
