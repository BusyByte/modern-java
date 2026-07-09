package dev.shawngarner;

import java.util.Arrays;
import java.util.List;

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
}
