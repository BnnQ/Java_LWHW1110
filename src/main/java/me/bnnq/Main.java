package me.bnnq;

import me.bnnq.datastructures.Pair;
import me.bnnq.enums.TaskResult;
import me.bnnq.results.Task5Result;
import me.bnnq.services.Task;
import me.bnnq.utils.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("ALL")
public class Main
{
    public static void main(String[] args)
    {
        //Task 1
        int[] array = ScannerUtilities.scanIntArray(5);

        try
        {
            Pair<Integer> minimumAndMaximum = task1(array);
            System.out.printf("Array: %s\n", Arrays.toString(array));
            System.out.printf("Minimum: %d\nMaximum: %d\n", minimumAndMaximum.getFirst(), minimumAndMaximum.getSecond());
        }
        catch (Exception exception)
        {
            System.err.println(exception.getMessage());
            System.exit(exception.hashCode());
        }

//        //Task 2
//        int[] array = ScannerUtilities.scanIntArray(5);
//
//        try
//        {
//            Pair<Double> sumAndAverage = task2(array);
//            System.out.printf("Array: %s\n", Arrays.toString(array));
//            System.out.printf("Sum: %f\nAverage: %f\n", sumAndAverage.getFirst(), sumAndAverage.getSecond());
//        }
//        catch (Exception exception)
//        {
//            System.err.println(exception.getMessage());
//            System.exit(exception.hashCode());
//        }

//        //Task 3
//        try
//        {
//            task3();
//        }
//        catch (Exception exception)
//        {
//            System.err.println(exception.getMessage());
//            System.exit(exception.hashCode());
//        }

//        //Task 4
//        try
//        {
//            task4();
//        }
//        catch (Exception exception)
//        {
//            System.err.println(exception.getMessage());
//            System.exit(exception.hashCode());
//        }

//        //Task 5
//        try
//        {
//            Task5Result result = task5();
//            System.out.printf("Array: %s\n", Arrays.toString(result.array()));
//            System.out.printf("Sum: %d\nAverage: %f\n", result.sum(), result.average());
//        }
//        catch (Exception exception)
//        {
//            System.err.println(exception.getMessage());
//            System.exit(exception.hashCode());
//        }

//        //Task 6
//        try
//        {
//            Queue<String> statistics = task6();
//            System.out.println("Statistics:");
//            for (String statistic : statistics)
//            {
//                System.out.println(statistic);
//            }
//        }
//        catch (Exception exception)
//        {
//            System.err.println(exception.getMessage());
//            System.exit(exception.hashCode());
//        }

        //Task 7
//        try
//        {
//            Queue<String> statistics = task7();
//            System.out.println("Statistics:");
//            for (String statistic : statistics)
//            {
//                System.out.println(statistic);
//            }
//        }
//        catch (Exception exception)
//        {
//            System.err.println(exception.getMessage());
//            System.exit(exception.hashCode());
//        }

//        //Task 8
//        try
//        {
//            Queue<String> statistics = task8();
//            System.out.println("Statistics:");
//            for (String statistic : statistics)
//            {
//                System.out.println(statistic);
//            }
//        }
//        catch (Exception exception)
//        {
//            System.err.println(exception.getMessage());
//            System.exit(exception.hashCode());
//        }

    }

    public static Pair<Integer> task1(int[] array) throws InterruptedException, ExecutionException
    {
        var minimumSearcherTask = Tasks.run(() -> {
            int minimum = array[0];
            for (int i = 1; i < array.length; i++)
            {
                if (array[i] < minimum)
                {
                    minimum = array[i];
                }
            }

            return minimum;
        });

        var maximumSearcherTask = Tasks.run(() -> {
            int maximum = array[0];
            for (int i = 1; i < array.length; i++)
            {
                if (array[i] > maximum)
                {
                    maximum = array[i];
                }
            }

            return maximum;
        });

        Tasks.waitAll(minimumSearcherTask, maximumSearcherTask);
        return new Pair<>(minimumSearcherTask.get(), maximumSearcherTask.get());
    }

    public static Pair<Double> task2(int[] array) throws ExecutionException, InterruptedException
    {
        var sumCalculatorTask = Tasks.run(() -> {
            double sum = 0;
            for (int element : array)
            {
                sum += element;
            }

            return sum;
        });

        var averageCalculatorTask = Tasks.run(() -> {
            double sum = 0;
            for (int element : array)
            {
                sum += element;
            }

            return sum / array.length;
        });

        Tasks.waitAll(sumCalculatorTask, averageCalculatorTask);
        return new Pair<>(sumCalculatorTask.get(), averageCalculatorTask.get());
    }

    public static void task3() throws IOException, InterruptedException
    {
        String filePath = ScannerUtilities.scanString("Enter file path: ");
        String separator = ScannerUtilities.scanString("Enter separator: ");

        Integer[] array = ArrayUtilities.parseIntArray(FileUtilities.readAllText(filePath), separator);
        System.out.printf("Array: %s\n", Arrays.toString(array));

        var evenNumbersWriterTask = Tasks.run(() -> {
            String content = Arrays.toString(Arrays.stream(array).filter(element -> element % 2 == 0).toArray());
            Files.writeString(Paths.get("even.txt"), content);
            System.out.println("Even numbers from array successfully written to even.txt");
            return TaskResult.SUCCESS;
        });

        var oddNumbersWriterTask = Tasks.run(() -> {
            String content = Arrays.toString(Arrays.stream(array).filter(element -> element % 2 != 0).toArray());
            Files.writeString(Paths.get("odd.txt"), content);
            System.out.println("Odd numbers from array successfully written to odd.txt");
            return TaskResult.SUCCESS;
        });

        Tasks.waitAll(evenNumbersWriterTask, oddNumbersWriterTask);
    }

    public static void task4() throws ExecutionException, InterruptedException
    {
        String filePath = ScannerUtilities.scanString("Enter file path: ");
        String wordToSearch = ScannerUtilities.scanString("Enter word to search: ");

        Task<Boolean> wordSearcherTask = Tasks.run(() -> {
            String content = FileUtilities.readAllText(filePath);
            return content.contains(wordToSearch);
        });

        System.out.printf("Word %s %s in file %s\n", wordToSearch, wordSearcherTask.get() ? "exists" : "does not exist", filePath);
    }

    public static Task5Result task5() throws InterruptedException, ExecutionException
    {
        Integer[] array = new Integer[5];

        Task<Integer[]> arrayFillerTask = new Task<>(() ->
        {
            for (int i = 0; i < array.length; i++)
            {
                array[i] = (int) (Math.random() * 21) - 10;
            }

            return array;
        });

        Task<Integer> sumCalculatorTask = new Task<>(() ->
        {
            int sum = 0;
            for (int element : array)
            {
                sum += element;
            }

            return sum;
        });

        Task<Double> averageCalculatorTask = new Task<>(() ->
        {
            double sum = 0;
            for (int element : array)
            {
                sum += element;
            }

            return sum / array.length;
        });

        arrayFillerTask.run();
        Tasks.waitAll(arrayFillerTask);
        sumCalculatorTask.run();
        averageCalculatorTask.run();
        Tasks.waitAll(sumCalculatorTask, averageCalculatorTask);

        return new Task5Result(array, sumCalculatorTask.get(), averageCalculatorTask.get());
    }

    public static Queue<String> task6() throws InterruptedException
    {
        String filePath = ScannerUtilities.scanString("Enter file path: ");

        Queue<String> statistics = new ConcurrentLinkedQueue<>();
        Task arrayFillerAndWriterTask = new Task(() ->
        {
            Integer[] array = new Integer[5];
            for (int i = 0; i < array.length; i++)
            {
                array[i] = (int) (Math.random() * 11);
            }

            String content = Arrays.toString(array).replace("[", "").replace("]", "").replace(" ", "");
            Files.writeString(Paths.get(filePath), content);
            statistics.add(String.format("Array %s successfully written to file %s\n", content, filePath));
            return TaskResult.SUCCESS;
        });

        Task primeNumbersInFileSearcherTask = new Task(() ->
        {
            String content = FileUtilities.readAllText(filePath);
            Integer[] array = ArrayUtilities.parseIntArray(content, ",");

            Integer[] primeNumbersArray = Arrays.stream(array).filter(element -> MathUtilities.isPrime(element))
                    .toArray(Integer[]::new);

            statistics.add(String.format("Prime numbers from file %s: %s\n", filePath, Arrays.toString(primeNumbersArray)));
            return TaskResult.SUCCESS;
        });

        Task factorialOfNumbersInFileCalculatorTask = new Task(() ->
        {
            String content = FileUtilities.readAllText(filePath);
            Integer[] array = ArrayUtilities.parseIntArray(content, ",");

            Integer[] factorialOfNumbersArray = Arrays.stream(array).map(element -> MathUtilities.factorial(element))
                    .toArray(Integer[]::new);

            statistics.add(String.format("Factorial of numbers from file %s: %s\n", filePath, Arrays.toString(factorialOfNumbersArray)));
            return TaskResult.SUCCESS;
        });

        arrayFillerAndWriterTask.run();
        Tasks.waitAll(arrayFillerAndWriterTask);

        primeNumbersInFileSearcherTask.run();
        factorialOfNumbersInFileCalculatorTask.run();
        Tasks.waitAll(primeNumbersInFileSearcherTask, factorialOfNumbersInFileCalculatorTask);

        return statistics;
    }

    public static Queue<String> task7() throws InterruptedException
    {
        String sourceDirectoryPath = ScannerUtilities.scanString("Enter existing directory path: ");
        String destinationDirectoryPath = ScannerUtilities.scanString("Enter destination directory path: ");

        Queue<String> statistics = new ConcurrentLinkedQueue<>();
        Task copierTask = Tasks.run(() ->
        {
            try
            {
                FileUtilities.copyDirectory(Paths.get(sourceDirectoryPath), Paths.get(destinationDirectoryPath));
                statistics.add(String.format("Directory %s successfully copied to %s\n", sourceDirectoryPath, destinationDirectoryPath));
                return TaskResult.SUCCESS;
            }
            catch (IOException exception)
            {
                statistics.add(String.format("Directory %s could not be copied to %s\n", sourceDirectoryPath, destinationDirectoryPath));
                return TaskResult.FAILURE;
            }
        });

        Tasks.waitAll(copierTask);
        return statistics;
    }

    public static Queue<String> task8() throws IOException, InterruptedException
    {
        String pathToDirectory = ScannerUtilities.scanString("Enter path to directory: ");
        String pathToFileWithBannedWords = ScannerUtilities.scanString("Enter path to file with banned words: ");
        String separator = ScannerUtilities.scanString("Enter separator: ");

        String[] bannedWords = ArrayUtilities.parseStringArray(FileUtilities.readAllText(pathToFileWithBannedWords), separator);
        String wordToSearch = ScannerUtilities.scanString("Enter word to search: ");

        String resultFilePath = pathToDirectory + "\\result.txt";
        Queue<String> statistics = new ConcurrentLinkedQueue<>();
        Task fileSearcherAndMergerTask = Tasks.run(() ->
        {
            try
            {
                var filesContainsWord = FileUtilities.searchFilesContainsWord(pathToDirectory, wordToSearch);
                FileUtilities.mergeFiles(resultFilePath, filesContainsWord.toArray(String[]::new));
                statistics.add(String.format("Files %s successfully merged to %s\n", Arrays.toString(filesContainsWord.toArray(String[]::new)), resultFilePath));
                return TaskResult.SUCCESS;
            }
            catch (IOException exception)
            {
                statistics.add(String.format("Files in directory %s could not be merged to %s\n", pathToDirectory, resultFilePath));
                return TaskResult.FAILURE;
            }
        });

        Task banwordsCutterTask = new Task(() ->
        {
            try
            {
                FileUtilities.replaceAllSubstringsTo(resultFilePath, "", bannedWords);
                statistics.add(String.format("Banned words successfully cutted from %s\n", resultFilePath));
                return TaskResult.SUCCESS;
            }
            catch (IOException exception)
            {
                statistics.add(String.format("Banned words could not be replaced to empty string in %s\n", resultFilePath));
                return TaskResult.FAILURE;
            }
        });

        Tasks.waitAll(fileSearcherAndMergerTask);
        banwordsCutterTask.run();
        Tasks.waitAll(banwordsCutterTask);

        return statistics;
    }

}