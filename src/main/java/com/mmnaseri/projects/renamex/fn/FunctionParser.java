package com.mmnaseri.projects.renamex.fn;

import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 9:04 PM)
 */
public interface FunctionParser {

    List<FunctionCall> parse(String text);

}
