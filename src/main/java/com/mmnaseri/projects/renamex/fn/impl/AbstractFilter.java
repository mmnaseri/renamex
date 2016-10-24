package com.mmnaseri.projects.renamex.fn.impl;

import java.io.File;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 11:11 PM)
 */
public abstract class AbstractFilter extends AbstractFunction<File, List> {

    public AbstractFilter() {
        super(File.class, List.class);
    }

}
