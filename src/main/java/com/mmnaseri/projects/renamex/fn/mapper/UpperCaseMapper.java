package com.mmnaseri.projects.renamex.fn.mapper;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.impl.AbstractMapper;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 12:07 AM)
 */
public class UpperCaseMapper extends AbstractMapper {
    @Override
    public String call(String input, CallContext context) {
        return input.toUpperCase();
    }
}
