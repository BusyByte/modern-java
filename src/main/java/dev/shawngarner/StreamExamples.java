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
}
