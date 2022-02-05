package Main;

import java.util.*;
import java.io.*;

import static java.lang.System.exit;

public class TSP {
// Instance Variables
private double distance = 0.0;
private Map<Coordinate, List<Coordinate>> graph;
private List<List<Coordinate>> input = new ArrayList<List<Coordinate>>();

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
            List<Coordinate> temp = new ArrayList<Coordinate>();
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
