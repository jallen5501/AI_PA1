package cs470hw1;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Stack;
import java.lang.StringBuilder;
import java.util.Iterator;

public class Search {

	public static void greedySearch(State[] states) {
	// Greedy search successful path to the goal state; this search works by choosing states that the 
	// program thinks is going to get it to the goal state, does not consider distance traveled
		
		System.out.println(greedyPath(states));	//print path results
	}
	
	private static String greedyPath(State[] states) {
		
		// Comparator: Sorts states in order of their return value for getHeuristic();
		PriorityQueue<State> pq = new PriorityQueue<>(State.getStateCount(), new Comparator<State>() {
            public int compare(State s1, State s2) {	
            	return (((Integer)s1.getHeustic()).compareTo((Integer)s2.getHeustic()));}});
		StringBuilder result = new StringBuilder();		// String is built as path progresses
		
		State current = states[0];							// Root
		while (!current.isGoal()) {							// Until the goal is found
			result.append(current + ">");					// add current state to path
			for (Iterator<State> it= current.getList(); it.hasNext(); )	
				pq.add(it.next());
			current = pq.remove();							// Move to next
			if (current.isGoal())	result.append(current);	// if goal: add final part to goal path, (Goal State)	
		}
		return result.toString();
	}

	public static void optimalSearch(State[] states, HashMap<String, Integer> map) {
		// Finding find the optimal path using A* algorithm 
		
		State root = states[0];		
		System.out.println(getOptimalPath(root, states, map));
	}
	
	private static String getOptimalPath(State root, State[] states, HashMap<String, Integer> map) {
		// Comparator: Sorts states in order of their return value for gefValue();
		// int edgeTo[]: algorithms can be led down different paths. this array will show 
		// optimal path from root to goal
		// boolean[] explored: marks whether node has been expanded
		
		PriorityQueue<State> pq = new PriorityQueue<>(1, new Comparator<State>() {
            public int compare(State s1, State s2) {
            	return (((Integer)s1.getfValue()).compareTo((Integer)s2.getfValue()));}});
		
		int[] edgeTo = new int[states.length];	// keeps track of specific paths
		for (int i = 0; i < edgeTo.length; i++) edgeTo[i] = Integer.MAX_VALUE;
		// state of edgeTo[] after search will give optimal path
		
		boolean[] explored = new boolean[states.length];		// marks whether node has been expanded
		for (int i = 0; i < states.length; i++)	explored[i] = false;
		
		State current = root;
		current.setgValue(0);
		while (!(current.isGoal())) {	// while (looking for goal)
			if (explored[current.getNumber()])	continue; 			// don't re-expand any nodes 
			else								explored[current.getNumber()] = true;
			for (Iterator<State> it= current.getList(); it.hasNext(); ) { // check states that this stat
				State temp = it.next();									// can move towards
				String link = String.format("%s-%s", current, temp);  
				temp.setgValue(current.getgValue() + map.get(link));	// set path cost to node in State
				temp.setfValue();										// pq<Comparator> compares fValue
				edgeTo[temp.getNumber()] = current.getNumber();			// make path
				pq.add(temp);											// add new state to pq
			}
			current = pq.poll();									
		}
		return printPath(current, edgeTo);
	}
	
	private static String printPath(State current, int[] edgeTo) {
		// Optimal path is found by reading the edgeTo[]. 
		// This function first pushes the goal on the stack and then the nodeNumber() of the node
		// in the edge to array. Repeated until root is pushed onto stack
		
		StringBuilder result = new StringBuilder();	// string is built after search is complete
		Stack<Integer> s = new Stack<>();
		int index = current.getNumber();
		s.push(index);		
		while (index != 0) {	// reverse the string and then print
			s.push(edgeTo[index]);
			index = edgeTo[index]; 
		}
		while (!s.isEmpty()) {
			int i = s.pop();
			result.append(i);
			if (i != State.getStateCount()) result.append(">");	
		}
		return result.toString();
	}
}