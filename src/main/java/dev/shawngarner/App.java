import dev.shawngarner.RecordsExamples;
import dev.shawngarner.StreamExamples;

/*
    Modern main function (introduced in Java 21):
     - no class or package needed
     - public and static not needed
     - no string args array
 */
void main() {

    var allExamples = Stream.of(
            new StreamExamples(),
            new RecordsExamples()
    );
    allExamples.forEach(example -> {
        IO.println("Running example: " + example.exampleName());
        example.runExamples();
        IO.println();
    });

}
