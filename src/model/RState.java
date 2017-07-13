package model;

import java.util.ArrayList;

public class RState implements Comparable<RState>{

	private ArrayList<RSubState> subStates;
	
	private int endsCount = 0; //Number of demonstrations ending in this state
	private int visitCounts = 0; //Number of path trough this state
	private double reward = -1; //Reward associated to this state
	private boolean goal = false;
	private boolean subGoal = false;
	
	@Override
	public boolean equals(Object obj) {
		
	 return this.toString().equals(obj.toString());
	}

	public RState() {
		super();
		subStates = new ArrayList<>();
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	public ArrayList<RSubState> getSubStates() {
		return subStates;
	}

	public void setSubStates(ArrayList<RSubState> subStates) {
		this.subStates = subStates;
	}
	
	public void addRSubState(RSubState rss){
		subStates.add(rss);
	}

	public int getEndsCount() {
		return endsCount;
	}

	public void setEndsCount(int endsCount) {
		this.endsCount = endsCount;
	}

	public int getVisitCounts() {
		return visitCounts;
	}

	public void setVisitCounts(int visitCounts) {
		this.visitCounts = visitCounts;
	}

	public double getReward() {
		return reward;
	}

	public void setReward(double reward) {
		this.reward = reward;
	}

	public boolean isGoal() {
		return goal;
	}

	public void setGoal(boolean goal) {
		this.goal = goal;
	}

	public boolean isSubGoal() {
		return subGoal;
	}

	public void setSubGoal(boolean subGoal) {
		this.subGoal = subGoal;
	}

	@Override
	public String toString() {
		String resp = "(";
		for (int i = 0; i < subStates.size(); i++) {
			resp+=subStates.get(i).toString()+"^";
		}
		resp = resp.substring(0,resp.length()-1);
		resp +=")";
		return resp;
	}

	@Override
	public int compareTo(RState o) {
		return subStates.toString().compareTo(o.getSubStates().toString());
	
	}
	
}
