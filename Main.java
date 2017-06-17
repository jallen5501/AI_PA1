package cs470hw1;
import java.io.*;
import java.util.HashMap;
public class Main {
	
	private static State[] states;		// data structure containing all states
	private static HashMap<String, Integer> map = new HashMap<>();
	private static BufferedReader in;	// read in file form input
	
	public static void main(String[] args) throws IOException {
		
		File file = new File("input1.txt");	// input file on command line
		in = new BufferedReader(new FileReader(file));
		String line = in.readLine();	// first line of input file
		String[] x = line.split(" ");
		int N = Integer.parseInt(x[0]); // number of states
		State.setTotalStates(N - 1);	// set static variable of state class
		states = new State[N];			// array that contains all states
		int i = 0;
		
		while (i < states.length)	states[i] = new State(i++);	// initialize state array 	 

		State source, destination;		 
		String key;						// reference in hashMap "source-destination"
		Integer value;					// path cost
		
		while ((line = in.readLine()) != null) {	// go through lines of input file
			x = line.split(" ");
			source = states[Integer.parseInt(x[0])];  
			destination = states[Integer.parseInt(x[1])];
			source.addRoute(destination);			// saving where states can go next
			key = String.format("%s-%s", source, destination);	
			value = Integer.parseInt(x[2]);			// lookup table for path costs
			map.put(key, value);
		}
		
		for (State current: states) 
			current.setHeuristic1();		// set first heuristicValue
		Search.greedySearch(states);		// print greedSearch path
		
		for (State current: states) 
			current.setHeuristic2();		// set different heuristicValue
		Search.optimalSearch(states, map);	// print A* search path
    }
}