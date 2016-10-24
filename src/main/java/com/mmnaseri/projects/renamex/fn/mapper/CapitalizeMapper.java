package com.mmnaseri.projects.renamex.fn.mapper;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.impl.AbstractMapper;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 2:03 AM)
 */
public class CapitalizeMapper extends AbstractMapper {

    @Override
    public String call(String input, CallContext context) {
        return input.isEmpty() ? input : input.substring(0, 1).toUpperCase().concat(input.substring(1));
    }

}
