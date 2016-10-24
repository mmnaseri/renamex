package com.mmnaseri.projects.renamex.scan.impl;

import com.mmnaseri.projects.renamex.scan.FileMetadata;

import java.io.File;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 2:31 AM)
 */
public class DefaultFileMetadata implements FileMetadata {

    private File file;
    private List<List<List<String>>> groups;

    @Override
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public List<List<List<String>>> getGroups() {
        return groups;
    }

    public void setGroups(List<List<List<String>>> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append('"').append(file).append("\" {\n");
        for (int i = 0; i < groups.size(); i++) {
            final List<List<String>> fragment = groups.get(i);
            builder.append("\t$").append(i + 1).append(" = {\n");
            for (int j = 0; j < fragment.size(); j++) {
                final List<String> groups = fragment.get(j);
                builder.append("\t\t").append(j + 1);
                if (groups.isEmpty()) {
                    builder.append(" = {}\n");
                    continue;
                }
                builder.append(" = {\n");
                for (int k = 0; k < groups.size(); k++) {
                    final String group = groups.get(k);
                    builder.append("\t\t\t").append(k).append(" = ").append(group).append("\n");
                }
                builder.append("\t\t}\n");
            }
            builder.append("\t}\n");
        }
        builder.append("}");
        return builder.toString();
    }
}
