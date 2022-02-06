package Main;

import java.time.*;
import java.util.*;
import java.io.*;

import static java.lang.System.exit;

public class TSP {
// Instance Variables
private double distance = 0.0;
private ArrayList<ArrayList<Coordinate>> input = new ArrayList<ArrayList<Coordinate>>();

// Methods
    // Large method run logic: add coordinates, calculate distances using heuristic and greedy while timing,
    // then output the answer for each. Additionally, output the times for each input.
    void runTSP(){
        parseInput();
//        heuristicAlgorithm();
        greedyAlgorithm();
    }

    // Run heurisitic algorithm
    void heuristicAlgorithm(){
        for(int i = 0; i < input.size(); i++) {
            System.out.println("Running setup for Heuristic Algorithm...");
            Map<Coordinate, ArrayList<Coordinate>> graph = new HashMap<Coordinate, ArrayList<Coordinate>>();
            initialization(graph,i);

            System.out.println("Running Heurstic Algorithm...");

            System.out.println("Finished running the Heuristic Algorithm!");
        }
    }

    // Run Greedy algorithm
    void greedyAlgorithm(){
        for(int i = 0; i < input.size(); i++) {
            System.out.println("Running setup on input " + (i+1) + " for Greedy Algorithm...");
            Map<Coordinate, ArrayList<Coordinate>> graph = new HashMap<Coordinate, ArrayList<Coordinate>>();
            initialization(graph,i);

            System.out.println("Running Greedy Algorithm...\n");

            ArrayList<Coordinate> visited = new ArrayList<Coordinate>();
            Coordinate startingCoord =  input.get(i).get(0);
            Coordinate endingCoord = input.get(i).get(0);

            long startTime = System.nanoTime();
            while(visited.size() < graph.size()){
                double min = -1.0;
                double diff = 0.0;
//                System.out.println("Starting coordinate is: " + "[" + startingCoord.x + ", " + startingCoord.y + "]");
                ArrayList<Coordinate> vals = graph.get(startingCoord);
                for(Coordinate temp : vals){
                    if(!visited.contains(temp)){
                        diff = distanceFormula(startingCoord,temp);
//                        System.out.println("Diff is: " + diff);
                        if(min == -1.0){
                            min = diff;
                            endingCoord = temp;
//                            System.out.println("Min is " + min + " and diff is " + diff +
//                                    ", so endingCoord is now [" + endingCoord.x + ", " + endingCoord.y + "]");
                        }
                        else{
                            if(min > diff){
                                min = diff;
                                endingCoord = temp;
//                                System.out.println("Min is " + min + " and diff is " + diff +
//                                        ", so endingCoord is now [" + endingCoord.x + ", " + endingCoord.y + "]");
                            }
                        }
                    }
                }
                distance += diff;
                visited.add(startingCoord);
                startingCoord = endingCoord;
            }
            visited.add(input.get(i).get(0));
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;

            printSummary(timeElapsed,visited);
            System.out.println("\nFinished running the Greedy Algorithm!\n");
        }
    }
    // Calculate the distance between two coordinates
    double distanceFormula(Coordinate start, Coordinate end){
        double dist = 0.0;
        double xOne,yOne,xTwo,yTwo;
        xOne = ((Integer) start.x).doubleValue();
        yOne = ((Integer) start.y).doubleValue();
        xTwo = ((Integer)end.x).doubleValue();
        yTwo = ((Integer)end.y).doubleValue();
        dist = Math.sqrt(Math.pow((xTwo - xOne),2.0) + Math.pow((yTwo - yOne),2.0));

        return dist;
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

    // Getters, Setters, and Misc. Helper methods
    void setDistance(double dist){
        this.distance = dist;
    }
    double getDistance(){
        return this.distance;
    }
}
