package cs470hw1;

import java.util.ArrayList;
import java.util.Iterator;

public class State {
	
	private static int stateCount;	// number of states in existence
	private final int N;			// number used to reference state
	private Integer hValue;			// Heuristic Value
	private final Boolean goal;		// is this a goal state?
	private Integer gValue;			// Path length from root to this.state
	private Integer fValue;			// g(n) + h(n)
	private ArrayList<State> list;
	
	public State(int stateNumber) {	
		this.N = stateNumber;
		this.goal = this.getNumber() == getStateCount() ? true : false;
		this.list = new ArrayList<State>();
		this.hValue = null;
		this.gValue = null;
		this.fValue = null;	
	}
	
	public static final void setTotalStates(int total) {	stateCount = total;	}
	public static int getStateCount() {		return stateCount;	} 
	
	public int getNumber() { return this.N;	}
	
	public boolean isGoal() {	return this.goal;	}
	
	public void addRoute(State destination) {	this.list.add(destination);	}
	public Iterator<State> getList() {	return this.list.iterator();	}
	
	public void setHeuristic1() {	// distance from the goal.	
		this.hValue = getStateCount() - this.getNumber();	
	}
	public void setHeuristic2() {	// distance from the goal - | # of outgoing paths - 1 | 
		// if the node has many nodes it can go to, 
		// then it is more likely part of the optimal path
		int extraPaths = list.size() -1;	// 
		this.hValue = getStateCount() - this.getNumber() - extraPaths; 
	}	
	public int getHeustic() {	return this.hValue;	}
	
	public void setgValue(int pathLength) {	this.gValue = pathLength;	}
	public int getgValue() {	return this.gValue;	}
	
	public void setfValue() {	this.fValue = this.gValue + this.hValue;	}
	public int getfValue() {	return this.fValue == null ? null : this.fValue;	}
	
	public String toString() {	return String.format("%s", this.getNumber());	}
}