package com.mmnaseri.projects.renamex.scan;

import com.mmnaseri.projects.renamex.conf.CompiledConfiguration;
import com.mmnaseri.projects.renamex.fn.FunctionCall;
import com.mmnaseri.projects.renamex.fn.FunctionRegistry;
import com.mmnaseri.projects.renamex.fn.filter.CompositeFilter;
import com.mmnaseri.projects.renamex.fn.impl.ImmutableCallContext;
import com.mmnaseri.projects.renamex.scan.impl.DefaultFileMetadata;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/23/16, 8:14 PM)
 */
public class FolderScanner {

    private final CompiledConfiguration configuration;
    private final FunctionRegistry functionRegistry;

    public FolderScanner(CompiledConfiguration configuration, FunctionRegistry functionRegistry) {
        this.configuration = configuration;
        this.functionRegistry = functionRegistry;
    }

    public List<FileMetadata> scan(File root) {
        final DefaultFileMetadata metadata = new DefaultFileMetadata();
        metadata.setFile(root);
        metadata.setGroups(new ArrayList<>());
        return scan(root, 0, metadata);
    }

    private List<FileMetadata> scan(File root, int current, DefaultFileMetadata parent) {
        //if the file name starts with `.` or this is not the last level and it isn't a directory we quit
        if (root.getName().startsWith(".") || current < configuration.getDepth() && !root.isDirectory()) {
            return Collections.emptyList();
        }
        final List<FunctionCall> calls = configuration.getFilters().get("$" + current);
        final DefaultFileMetadata metadata = new DefaultFileMetadata();
        metadata.setFile(root);
        metadata.setGroups(copy(parent.getGroups()));
        if (calls == null) {
            return collect(root, current, metadata);
        } else {
            final List<List<String>> groups = new ArrayList<>();
            metadata.getGroups().add(groups);
            for (FunctionCall call : calls) {
                final CompositeFilter filter = (CompositeFilter) functionRegistry.get(call.getName());
                final List<String> result = filter.call(root, new ImmutableCallContext(functionRegistry, metadata.getGroups(), call.getArguments()));
                if (result == null) {
                    return Collections.emptyList();
                } else {
                    groups.add(result);
                }
            }
            return collect(root, current, metadata);
        }
    }

    private List<FileMetadata> collect(File root, int current, DefaultFileMetadata metadata) {
        if (current == configuration.getDepth()) {
            return Collections.singletonList(metadata);
        } else {
            final File[] children = root.listFiles();
            final List<FileMetadata> result = new ArrayList<>();
            assert children != null;
            for (File child : children) {
                result.addAll(scan(child, current + 1, metadata));
            }
            return result;
        }
    }

    private List<List<List<String>>> copy(List<List<List<String>>> groups) {
        final List<List<List<String>>> fragments = new ArrayList<>(groups.size());
        for (List<List<String>> fragment : groups) {
            fragments.add(fragment.stream().map(ArrayList::new).collect(Collectors.toList()));
        }
        return fragments;
    }

}
