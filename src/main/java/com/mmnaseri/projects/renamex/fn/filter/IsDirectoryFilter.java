package com.mmnaseri.projects.renamex.fn.filter;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.impl.AbstractFilter;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 11:25 PM)
 */
public class IsDirectoryFilter extends AbstractFilter {
    @Override
    public List<String> call(File input, CallContext context) {
        return input.isDirectory() ? Collections.emptyList() : null;
    }
}
