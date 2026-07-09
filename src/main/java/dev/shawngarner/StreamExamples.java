package dev.shawngarner;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Streams introduced in Java 8
 */
public class StreamExamples {

    /*
        Output:
            SCALA
            SCHEME
            Have I learned Elixir? false
     */
    public List<String> progLangsExample() {
        // modern var declaration added in Java 10 infers type
        var languages = Arrays.asList(
                Arrays.asList("Java", "C++", "Scheme"),
                Arrays.asList("HTML", "JavaScript"),
                Arrays.asList("Groovy", "C#"),
                Arrays.asList("Scala", "Haskell", "Typescript"),
                List.of("Python"),
                Arrays.asList("Unison", "Rust")
        );

        var sLanguages = languages.stream()
                .flatMap(List::stream)
                .filter(s -> s.startsWith("S"))
                .map(String::toUpperCase)
                .distinct()
                .sorted()
                .peek(IO::println)
                .toList();

        var containsElixir = languages.stream()
                .anyMatch(s -> s.contains("Elixir"));
        IO.println("Have I learned Elixir? " + containsElixir); // Modern println - no System.out needed

        return sLanguages;
    }

    /*
        Output:
            I worked on Fri of this week.
            I worked on Mon of this week.
            I worked on Wed of this week.
            I worked on Thu of this week.
            I worked on Tue of this week.

            I worked on Mon of this week.
            I worked on Tue of this week.
            I worked on Wed of this week.
            I worked on Thu of this week.
            I worked on Fri of this week.

        Items are not processed in order but final list maintains order.
     */
    public List<String> parDaysOfWeekWorkedExample() {
        var daysOfWeek = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        var daysWorked = daysOfWeek.stream().parallel()
                .filter(d -> !d.startsWith("S"))
                .map(d ->"I worked on " + d + " of this week.")
                .peek(IO::println)
                .toList();
        daysWorked.forEach(IO::println);
        return daysWorked;
    }

    /*
        Output:
            4
            16
            36
            64

        Specialized stream for primitive int doesn't box values to Integer objects, which is more efficient.
     */
    public int [] numbersExample() {
        return IntStream.range(1, 10)
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .peek(IO::println)
                .toArray();
    }

    /*
        Only 2 at a time will be processed in the ForkJoinPool
        Output:
            it's a bar: ForkJoinPool-1-worker-2
            it's a baz: ForkJoinPool-1-worker-1
            it's a foo: ForkJoinPool-1-worker-2
            it's a bam: ForkJoinPool-1-worker-1
            it's a bif: ForkJoinPool-1-worker-2
     */
    public List<String> controlledParallelExample() {
        int parallelism = 2;
        var pool = new ForkJoinPool(parallelism);

        try {
            return pool.submit(() -> Stream.of("foo", "bar", "baz", "bif", "bam")
                    .parallel()
                    .filter(s -> s.length() == 3)
                    .map(s -> "it's a " + s)
                    .peek(s -> IO.println(s + ": " + Thread.currentThread().getName()))
                    .toList()).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            pool.shutdown();
        }
    }
}
