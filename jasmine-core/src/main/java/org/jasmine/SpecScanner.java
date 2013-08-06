package org.jasmine;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class SpecScanner {
    private final FileSystem fileSystem = FileSystems.getDefault();

    public Iterable<String> findSpecs(String pattern){
        if(!pattern.startsWith("./")){
            pattern = "./" + pattern;
        }
        final PathMatcher pathMatcher = fileSystem.getPathMatcher("glob:" + pattern);

        final List<String> paths = newArrayList();
        try{
            Files.walkFileTree(fileSystem.getPath("."), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(final Path file, BasicFileAttributes attrs) throws IOException {
                    if (pathMatcher.matches(file)) {
                        paths.add(file.toString());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return paths;
    }
}
