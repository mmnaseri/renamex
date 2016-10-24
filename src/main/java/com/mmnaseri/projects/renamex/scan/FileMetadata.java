package com.mmnaseri.projects.renamex.scan;

import java.io.File;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (10/24/16, 2:30 AM)
 */
public interface FileMetadata {

    File getFile();

    List<List<List<String>>> getGroups();

}
