package Main;

import java.util.*;
import java.io.*;

import static java.lang.System.exit;

public class TSP {
// Instance Variables
private double distance = 0.0;
private Map<Coordinate, List<Coordinate>> graph;
private List<Coordinate> input;

// Methods
    // Large method run logic: add coordinates, calculate distances using heuristic and greedy while timing,
        // then output the answer for each. Additionally, output the times for each input.
    void runTSP(){

    }
    // Run heurisitic algorithm
    // Run Greedy algorithm
    // Add a coordinate to the graph
    // Calculate the distance between two coordinates
    // Output answer for algorithm + time
    // Parse input and transform into a List of Coordinates
    void parseInput(){
        List<String> names = new ArrayList<String>();
        File[] files = new File("/Users/justinorji/Desktop/CSCI 406/Orji_TSP/src/Input Files").listFiles();

        for(File file : files) {
            if (file.isFile()) {
                names.add(file.getName());
            }
        }

        for(String name : names) {
            File myObj = new File("/Users/justinorji/Desktop/CSCI 406/Orji_TSP/src/Input Files/" + name);
            Scanner myReader = null;
            try {
                myReader = new Scanner(myObj);
            } catch (FileNotFoundException e) {
                System.out.println("Error reading myObj into scanner. Printing trace then quitting...");
                e.printStackTrace();
                exit(0);
            }
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        }
    }


    // Getters, Setters, and Misc. Helper methods
    void setDistance(double dist){
        this.distance = dist;
    }
    double getDistance(){
        return this.distance;
    }
}
