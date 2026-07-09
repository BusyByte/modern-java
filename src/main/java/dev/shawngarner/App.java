import dev.shawngarner.StreamExamples;

/*
    Modern main function (introduced in Java 21):
     - no class or package needed
     - public and static not needed
     - no string args array
 */
void main() {
    var streamExamples = new StreamExamples();
    streamExamples.progLangsExample();
    streamExamples.parDaysOfWeekWorkedExample();
    streamExamples.numbersExample();
    streamExamples.controlledParallelExample();
}
