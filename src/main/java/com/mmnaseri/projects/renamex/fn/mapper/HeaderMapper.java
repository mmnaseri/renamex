package com.mmnaseri.projects.renamex.fn.mapper;

import com.mmnaseri.projects.renamex.fn.CallContext;
import com.mmnaseri.projects.renamex.fn.impl.AbstractMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 2:04 AM)
 */
public class HeaderMapper extends AbstractMapper {

    private static final Set<String> prepositions = new HashSet<>();

    static {
        prepositions.add("the");
        prepositions.add("into");
        prepositions.add("via");
        prepositions.add("than");
        prepositions.add("upon");
        prepositions.add("with");
        prepositions.add("within");
        prepositions.add("without");
    }

    @Override
    public String call(String input, CallContext context) {
        final String[] words = input.trim().toLowerCase().split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
            if (i == 0 || i == words.length - 1) {
                continue;
            }
            if (words[i].length() == 2 || prepositions.contains(words[i])) {
                words[i] = words[i].toLowerCase();
            }
        }
        return Arrays.stream(words).reduce((first, second) -> first + " " + second).orElse("");
    }

}
