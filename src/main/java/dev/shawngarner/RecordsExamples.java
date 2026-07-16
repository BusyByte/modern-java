package dev.shawngarner;

import java.util.HashMap;
import java.util.HashSet;

/*
    Records were formally introduced in Java 16.
    You get:
     - a default constructor
     - accessor methods (getters but not JavaBean conventions of getName(), instead name())
     - equals, hashCode, and toString

     Are final but can implement interfaces.


 */
public class RecordsExamples implements ExampleRunner {

    @Override
    public String exampleName() {
        return "RecordsExamples";
    }

    @Override
    public void runExamples() {
        basics();
        equalsExample();
        hashCodeExample();
        patternMatchingExample();
    }

    /*
            Output:
                Exception thrown as expected: Coordinates must be non-negative
                Manhattan Distance: 200.0
                Euclidean Distance: 141.42136
                Origin: Point[x=0, y=0]
                Destination: Point[x=100, y=100]
                Dest: (100, 100)
         */
    public void basics() {
        IO.println("running basics");
        try {
            new Point(-1, -1);
        } catch (IllegalArgumentException e) {
            IO.println("Exception thrown as expected: " + e.getMessage());
        }

        var origin =  new Point(0, 0);
        var destination = new Point(100, 100);

        var mDistance = destination.manhattanDistance(origin);
        var eDistance = destination.euclideanDistance(origin);
        IO.println("Manhattan Distance: " + mDistance);
        IO.println("Euclidean Distance: " + eDistance);

        // Uses the default generated toString
        IO.println("Origin: " + origin);
        IO.println("Destination: " + destination);

        // Uses the default generated accessors for x and y fields
        IO.println("Dest: (" + destination.x() + ", " + destination.y() + ")");
        IO.println();
    }

    /*
        Output:
            Origins equal: true
            Origin equal equals dest: false
     */
    public void equalsExample() {
        IO.println("Running equals example");
        var origin =  new Point(0, 0);
        var otherOrigin = new Point(0, 0);
        var destination = new Point(100, 100);

        // Uses the default generated equals method
        IO.println("Origins equal: " + origin.equals(otherOrigin)); // true
        IO.println("Origin equal equals dest: " + origin.equals(destination)); // false
        IO.println();
    }

    /*
        Output:
            Origin hash: 0
            Other Origin hash: 0
            Destination hash: 3200
            Contains Destination: true
            Set size: 2
            Already In Set: false
            Set contains other origin: false
            Set contains origin: true
            Map size: 2
            Origin: 1
            Replaces Original Value: 0
            Contains Other Origin: false
            Contains Origin: true
     */
    public void hashCodeExample() {
        IO.println("Running hashCode example");
        var origin =  new Point(0, 0);
        var otherOrigin = new Point(0, 0);
        var destination = new Point(100, 100);

        var originHash = origin.hashCode();
        var otherOriginHash = otherOrigin.hashCode();
        IO.println("Origin hash: " + originHash); // 0
        IO.println("Other Origin hash: " + otherOriginHash); // 0

        var destinationHash = destination.hashCode();
        IO.println("Destination hash: " + destinationHash); // 3200

        var set = new HashSet<Point>();
        set.add(destination);
        set.add(origin);
        var alreadyInSet = set.add(otherOrigin);

        // Hash to same key values so set addition and lookup is efficient and equals will make it replace existing origin
        var containsDestination = set.contains(destination);
        IO.println("Contains Destination: " + containsDestination); // true
        IO.println("Set size: " + set.size()); // 2 since only origin and dest
        IO.println("Already In Set: " + alreadyInSet); // true

        // check by reference to see which is actually in the set
        var setContainsOtherOrigin = set.stream().anyMatch(point -> point == otherOrigin);
        var setContainsOrigin = set.stream().anyMatch(point -> point == origin);
        IO.println("Set contains other origin: " + setContainsOtherOrigin); // should be false
        IO.println("Set contains origin: " + setContainsOrigin); // should be true


        // Hash to same key values so map addition and lookup is efficient and equals will make it replace existing origin
        var map = new HashMap<Point, Integer>();
        map.put(destination, 0);
        map.put(origin, 0);
        var replacedOrigin = map.put(otherOrigin, 1);

        var originValue = map.get(origin);

        IO.println("Map size: " + map.size()); // should only be 2 since origins match up
        IO.println("Origin: " + originValue); // should be the value of 1 which replaced the 0
        IO.println("Replaces Original Value: " + replacedOrigin); // should be 0 since it was the value which was replaces by 1

        // compare by reference should indicate we have the original one
        var containersOtherOrigin = map.entrySet().stream().anyMatch(entry -> entry.getKey() == otherOrigin);
        var containsOrigin = map.entrySet().stream().anyMatch(entry -> entry.getKey() == origin);
        IO.println("Contains Other Origin: " + containersOtherOrigin); // should be false
        IO.println("Contains Origin: " + containsOrigin); // should be true
        IO.println();
    }

    /*
        Output:
            Matched Origin: Origin
            Instanceof found Origin
     */
    public void patternMatchingExample() {
        IO.println("Running pattern matching example");
        Object origin =  new Point(0, 0);
        var matchResult = switch (origin) {
            case Point(int x, int y) when x == 0 && y == 0 -> "Origin";
            default -> "Non-Origin";
        };

        IO.println("Matched Origin: " + matchResult);

        if( origin instanceof Point(var x, var y) && x == 0 && y == 0) {
            IO.println("Instanceof found Origin");
        } else {
            IO.println("Instanceof Non-origin: " + matchResult);
        }
        IO.println();
    }


}
