package Main;

import java.util.*;
import java.io.*;

import static java.lang.System.exit;
import static java.lang.System.in;

public class TSP {
// Instance Variables
private double distance = 0.0;
private ArrayList<ArrayList<Coordinate>> input = new ArrayList<ArrayList<Coordinate>>();
private ArrayList<ArrayList<Coordinate>> permutations = new ArrayList<ArrayList<Coordinate>>();

// Methods
    // Large method run logic: add coordinates, calculate distances using heuristic and greedy while timing,
    // then output the answer for each. Additionally, output the times for each input.
    void runTSP(){
        parseInput();
        exhaustiveAlgorithm();
//        greedyAlgorithm();
    }

    // Run exhaustive algorithm
    void exhaustiveAlgorithm(){
        for(int i = 0; i < input.size(); i++) {
            distance = 0.0;
            System.out.println("Running setup for Exhaustive Algorithm...");
            Map<Coordinate, ArrayList<Coordinate>> graph = new HashMap<Coordinate, ArrayList<Coordinate>>();
            initialization(graph,i);

            System.out.println("Running Exhaustive Algorithm...\n");

            ArrayList<Coordinate> copy = new ArrayList<Coordinate>(input.get(i));
            findPermutations(copy.size(),copy);

            System.out.println("\nFinished running the Exhaustive Algorithm!\n");
        }
    }

    // Run Greedy algorithm
    void greedyAlgorithm(){
        for(int i = 0; i < input.size(); i++) {
            distance = 0.0;
            // Setup includes populating the graph for each input
            System.out.println("Running setup on input " + (i+1) + " for Greedy Algorithm...");
            Map<Coordinate, ArrayList<Coordinate>> graph = new HashMap<Coordinate, ArrayList<Coordinate>>();
            initialization(graph,i);

            System.out.println("Running Greedy Algorithm...\n");

            // Variables for coordinates in algorithm loop declared
            ArrayList<Coordinate> visited = new ArrayList<Coordinate>();
            Coordinate startingCoord =  input.get(i).get(0);
            Coordinate endingCoord = input.get(i).get(0);

            // Time starts, algorithm starts
            long startTime = System.nanoTime();

            // Algorithm ends once every point has been visited
            while(visited.size() < graph.size()){
                // Variable declarations
                double min = -1.0;
                double diff;
                double finalDiff = 0.0;

                // Values for that specific coordinate are loaded in
                ArrayList<Coordinate> vals = graph.get(startingCoord);
                for(Coordinate temp : vals){
                    // As long as the chosen coordinate is not in visited, the
                    // if condition will run
                    if(!visited.contains(temp)){
                        // Distance between the end coordinate and starting is calculated,
                        // then checked against the current min. If the conditions are met,
                        // then the current min, finalDiff for the loop, and endingCoord are all updated.
                        diff = distanceFormula(startingCoord,temp);
                        if(min == -1.0){
                            min = diff;
                            finalDiff = min;
                            endingCoord = temp;
                        }
                        else{
                            if(min > diff){
                                min = diff;
                                finalDiff = min;
                                endingCoord = temp;
                            }
                        }
                    }
                }
                // Distance between points is added onto the output distance, the current starting coordinate
                // is added to the visited list, and then the starting coordinate
                // is set to the current ending coordinate.
                distance += finalDiff;
                visited.add(startingCoord);
                startingCoord = endingCoord;
            }
            // Finally, the "loop" back to the beginning is added, the distance is calculated,
            // and time finally ends and is also calcualted.
            visited.add(input.get(i).get(0));
            distance += distanceFormula(visited.get(visited.size()-1),visited.get(visited.size()-2));
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;

            // Summary prints for that input
            printSummary(timeElapsed,visited);
            System.out.println("\nFinished running the Greedy Algorithm!\n");
        }
    }

    // Calculate the distance between two coordinates
    double distanceFormula(Coordinate start, Coordinate end){
        double xOne,yOne,xTwo,yTwo;
        xOne = ((Integer) start.x).doubleValue();
        yOne = ((Integer) start.y).doubleValue();
        xTwo = ((Integer)end.x).doubleValue();
        yTwo = ((Integer)end.y).doubleValue();
        double dist = Math.sqrt(Math.pow((xTwo - xOne),2.0) + Math.pow((yTwo - yOne),2.0));

        return dist;
    }

    // Recursive algorithm to find permutations
    void findPermutations(int size, ArrayList<Coordinate> elements){
        if(size == 1){
            savePermutation(elements);
        }
        else{
            for(int i = 0; i < size-1; i++){
                findPermutations(size-1,elements);
                if(size % 2 == 0){
                    Collections.swap(elements,i,size-1);
                }
                else{
                    Collections.swap(elements,0,size-1);
                }
            }
            findPermutations(size-1,elements);
        }
    }

    // Helper function for findPermutations; adds the permutation to an arrayList
    void savePermutation(ArrayList<Coordinate> next){
        permutations.add(next);
        System.out.println("Permutation to be saved: ");
        for(Coordinate thing : next){
            System.out.println("[" + thing.x + ", " + thing.y + "]");
        }
        System.out.println();
    }

    // Output answer for algorithm + time
    void printSummary(long elapsed, ArrayList<Coordinate> route){
        System.out.println(distance);
        for(Coordinate coord : route){
            System.out.println("[" + coord.x + ", " + coord.y + "]");
        }
        System.out.println(elapsed);
    }

    // Initialize the algorithms
    void initialization(Map<Coordinate, ArrayList<Coordinate>> obj, int index){
        for(int j = 0; j < input.get(index).size(); j++){
            ArrayList<Coordinate> copy = new ArrayList<Coordinate>(input.get(index));
            copy.remove(j);
            obj.put(input.get(index).get(j),copy);
        }

        //            for(Map.Entry coord : graph.entrySet()){
        //                Coordinate key = (Coordinate)coord.getKey();
        //                System.out.println("The coordinate for this set is: " + "[" + key.x + ", " + key.y + "]");
        //                List<Coordinate> value = (List<Coordinate>)coord.getValue();
        //                System.out.println("These are the values for the set: ");
        //                for(Coordinate k : value){
        //                    System.out.println("[" + k.x + ", " + k.y + "]");
        //                }
        //            }
    }

    // Parse input and transform into a List of Coordinates
    void parseInput(){
        // Variable setup
        List<String> names = new ArrayList<String>();
        File[] files = new File("/Users/justinorji/Desktop/CSCI 406/Orji_TSP/src/Input Files").listFiles();

        // File names read from input folder
        for(File file : files) {
            if (file.isFile()) {
                names.add(file.getName());
            }
        }

        // File names sorted and main work begins
        names.sort(Comparator.naturalOrder());
        for(String name : names) {
            // File names are read in as file objects. Try-catch verifies Scanner object
            File myObj = new File("/Users/justinorji/Desktop/CSCI 406/Orji_TSP/src/Input Files/" + name);
            Scanner myReader = null;
            try {
                myReader = new Scanner(myObj);
            } catch (FileNotFoundException e) {
                System.out.println("Error reading myObj into scanner. Printing trace then quitting...");
                e.printStackTrace();
                exit(0);
            }
            // Private input variable is populated with ArrayLists containing Coordinates for each input file
            int tracker = 0;
            ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
            input.add(temp);
            while (myReader.hasNextLine()) {
                if(tracker == 0) myReader.nextLine();
                else {
                    String data = myReader.nextLine();
                    String [] parsed = data.split(" ");
                    int x = Integer.parseInt(parsed[0]);
                    int y = Integer.parseInt(parsed[1]);
                    Coordinate tempo = new Coordinate(x,y);
                    input.get(input.size() - 1).add(tempo);
                }
                tracker++;
            }
            myReader.close();
        }
//        Commented out verification code for debugging
//        int counter = 0;
//        for(List<Coordinate> i : input){
//            System.out.println(counter);
//            for(Coordinate j : i){
//                System.out.println("[" + j.x + ", " + j.y +"]");
//            }
//            counter++;
//        }
    }

}
