package me.bnnq.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FileUtilities
{
    public static String readAllText(String pathToFile) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(pathToFile)));
    }

    public static void replaceAllSubstringsTo(String filePath, String replacement, String... substrings) throws IOException
    {
        String content = readAllText(filePath);
        for (String substring : substrings)
            content = content.replaceAll(substring, replacement);

        Files.write(Paths.get(filePath), content.getBytes());
    }

    public static String[] findExceptLines(String firstFilePath, String secondFilePath) {
        Set<String> firstFileLines = new HashSet<>();
        Set<String> secondFileLines = new HashSet<>();

        try {
            firstFileLines = new HashSet<>(Files.readAllLines(Paths.get(firstFilePath)));
            secondFileLines = new HashSet<>(Files.readAllLines(Paths.get(secondFilePath)));
        } catch (IOException exception) {
            System.err.println("File reading error: " + exception.getMessage());
        }

        Set<String> finalSecondFileLines = secondFileLines;
        return firstFileLines.stream()
                .filter(line -> !finalSecondFileLines.contains(line))
                .toArray(String[]::new);
    }

    public static String findLongestLine(String filePath) throws IOException {
        var stream = Files.lines(Paths.get(filePath));
        var longestLine = stream.max(Comparator.comparingInt(String::length)).orElse(null);

        stream.close();
        return longestLine;
    }

    public static void copy(Path source, Path destination) throws IOException {
        Files.copy(source, destination);
    }

    public static void copyDirectory(Path source, Path destination) throws IOException {

        try (var stream = Files.walk(source))
        {
            stream.forEach(sourcePath -> {
            try {
                Files.copy(sourcePath, destination.resolve(source.relativize(sourcePath)));
            } catch (IOException exception) {
                System.err.println("File copying error: " + exception.getMessage());
            }
        });
        }
        catch (Exception exception)
        {
            System.err.println("File copying error: " + exception.getMessage());
            throw exception;
        }

    }

    public static void mergeFiles(String resultFilePath, String... filesPaths)
    {
        try (var writer = Files.newBufferedWriter(Paths.get(resultFilePath)))
        {
            for (String filePath : filesPaths)
            {
                var reader = Files.newBufferedReader(Paths.get(filePath));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    writer.write(line);
                    writer.newLine();
                }
                reader.close();
            }
        }
        catch (IOException exception)
        {
            System.err.println("File merging error: " + exception.getMessage());
        }
    }

    public static Collection<String> searchFilesContainsWord(String directoryPath, String word) throws IOException {
        ArrayList<String> filePaths = new ArrayList<>();
        Files.walkFileTree(Paths.get(directoryPath), new SimpleFileVisitor<>()
        {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
            {
                if (containsWord(file.toString(), word))
                {
                    filePaths.add(file.toString());
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return filePaths;
    }

    private static boolean containsWord(String filePath, String word) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(word)) {
                    return true;
                }
            }
        }
        return false;
    }


}