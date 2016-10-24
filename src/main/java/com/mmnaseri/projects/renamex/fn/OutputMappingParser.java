package com.mmnaseri.projects.renamex.fn;

import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 1:34 AM)
 */
public interface OutputMappingParser {

    List<FunctionCall> parse(String text);

}
