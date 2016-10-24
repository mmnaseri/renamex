package com.mmnaseri.projects.renamex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmnaseri.projects.renamex.conf.CompiledConfiguration;
import com.mmnaseri.projects.renamex.conf.Configuration;
import com.mmnaseri.projects.renamex.fn.FunctionCall;
import com.mmnaseri.projects.renamex.fn.impl.DefaultFunctionRegistry;
import com.mmnaseri.projects.renamex.fn.impl.ImmutableCallContext;
import com.mmnaseri.projects.renamex.fn.mapper.CompositeMapper;
import com.mmnaseri.projects.renamex.scan.FileMetadata;
import com.mmnaseri.projects.renamex.scan.FolderScanner;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/22/16, 2:51 AM)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        args = new String[]{"--config=/Users/milad/Projects/Java/renamex/src/main/resources/sample.yaml", "/Users/milad/a"};
        if (args.length == 0) {
            wrongUsage();
        }
        final String target = args[args.length - 1];
        final File root = new File(target);
        if (!root.exists()) {
            System.out.println("Directory does not exist: `" + target + "`");
            wrongUsage();
        }
        if (!root.isDirectory()) {
            System.out.println("Target is not a directory: `" + target + "`");
            wrongUsage();
        }
        boolean dryrun = false;
        Configuration configuration = new Configuration();
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("--dryrun")) {
                dryrun = true;
            } else if (args[i].startsWith("--config=")) {
                final String configLocation = args[i].substring("--config=".length());
                final File configFile = new File(configLocation);
                if (!configFile.exists()) {
                    System.out.println("Configuration file does not exist: " + configLocation);
                    wrongUsage();
                }
                if (!configFile.isFile()) {
                    System.out.println("Configuration file is not a real file");
                    wrongUsage();
                }
                if (configLocation.toLowerCase().endsWith(".json")) {
                    final ObjectMapper mapper = new ObjectMapper();
                    configuration = mapper.readValue(configFile, Configuration.class);
                } else if (configLocation.toLowerCase().endsWith(".yaml") || configLocation.toLowerCase().endsWith(".yml")) {
                    final Yaml yaml = new Yaml(new Constructor(Configuration.class));
                    configuration = ((Configuration) yaml.load(new FileReader(configFile)));
                }
            } else {
                System.out.println("Unrecognized option: " + args[i]);
                wrongUsage();
            }
        }
        if (configuration.getIn() == null) {
            configuration.setIn("$1");
        }
        if (configuration.getOut() == null) {
            configuration.setOut(configuration.getIn());
        }
        if (configuration.getFilters() == null) {
            configuration.setFilters(new HashMap<>());
        }
        configuration.setDryrun(configuration.getDryrun() || dryrun);
        if (!configuration.getIn().matches("\\$\\d+(/\\$\\d+)*")) {
            System.out.println("Input format must be of the type: $1/$2/.../$n");
            wrongUsage();
        }
        final CompiledConfiguration compiled = configuration.compile();
        final DefaultFunctionRegistry functionRegistry = new DefaultFunctionRegistry();
        final FolderScanner scanner = new FolderScanner(compiled, functionRegistry);
        final List<FileMetadata> list = scanner.scan(root);
        final FunctionCall outputCall = compiled.getOutput();
        final CompositeMapper mapper = (CompositeMapper) functionRegistry.get(outputCall.getName());
        System.out.println("Discovered " + list.size() + " file(s).");
        if (compiled.isDryRun()) {
            System.out.println("This is a dry run. No files will be modified.");
        }
        for (int i = 0; i < list.size(); i++) {
            final FileMetadata metadata = list.get(i);
            final File file = metadata.getFile();
            final String newName = mapper.call(file, new ImmutableCallContext(functionRegistry, metadata.getGroups(), outputCall.getArguments()));
            final String originalName = file.getAbsolutePath().substring(root.getAbsolutePath().length() + 1);
            System.out.print("[" + (i + 1) + "/" + list.size() + "] ");
            System.out.println("Renaming `" + originalName + "` to `" + newName + "`");
            if (!compiled.isDryRun()) {
                final File newFile = new File(root.getAbsolutePath() + "/" + newName);
                final File parentFile = newFile.getParentFile();
                if (!parentFile.exists()) {
                    if (!parentFile.mkdirs()) {
                        System.out.println(" >> Could not create parent directory `" + parentFile.getAbsolutePath() + "`");
                        continue;
                    }
                } else if (!parentFile.isDirectory()) {
                    System.out.println(" >> Parent location is not a directory. Ignoring.");
                    continue;
                }
                if (newFile.exists()) {
                    System.out.println(" >> File already exists. Ignoring.");
                    continue;
                }
                if (!file.renameTo(newFile)) {
                    System.out.println(" >> Could not rename the file");
                }
            }
        }
    }

    private static void wrongUsage() {
        System.out.println("Usage: renamex [options] dir");
        System.exit(1);
    }

}
