package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import helpers.RelationalConverter;
import helpers.TraceParser;
import model.Demonstration;
import model.RAction;
import model.RState;

public class Main {

	static String[] X_RELATIVE = new String[] { "LEFT", "RIGHT", "-" };
	static String[] Y_RELATIVE = new String[] { "UP", "DOWN", "-" };
	static String[] values = new String[] { "ON", "CLOSE", "MIDDLE", "FAR" };
	static int[] range = new int[] { 0, 1, 3, 6 };

	static double learningRate = 0.9;
	static double discountFactor = 0.8;
	static double goalReward = 100;
	static double subGoalReward = 50;

	public static void main(String[] args) throws Exception {
		ArrayList<Demonstration> demonstrations = TraceParser.parseFile("data/task1.txt", 3);

		ArrayList<ArrayList<RState>> demostrationsStates = new ArrayList<>();
		ArrayList<ArrayList<RAction>> demostrationsActions = new ArrayList<>();

		for (int i = 0; i < demonstrations.size(); i++) {
			Demonstration d = demonstrations.get(i);

			ArrayList<RState> all_states = RelationalConverter.convertToRState(d, X_RELATIVE, Y_RELATIVE, values);

			ArrayList<RState> d_r_states = new ArrayList<>();
			ArrayList<RAction> r_actions = new ArrayList<>();
			RelationalConverter.generateStateActions(all_states, d, d_r_states, r_actions);
			demostrationsStates.add(d_r_states);
			demostrationsActions.add(r_actions);

		}

		HashMap<RState, Integer> labelState = new HashMap<>();
		HashMap<RAction, Integer> labelAction = new HashMap<>();

		HashSet<RState> subGoalStates = new HashSet<>();
		HashMap<RState,Integer> finalStateCount = new HashMap<>();

		int labelStateCount = 0;
		int labelActionCount = 0;

		for (int j = 0; j < demostrationsStates.size(); j++) {

			ArrayList<RState> rStateList = demostrationsStates.get(j);
			ArrayList<RAction> rActionList = demostrationsActions.get(j);

			for (int i = 0; i < rStateList.size(); i++) {

				RState rState = rStateList.get(i);
				if (i < rStateList.size() - 1) {
					RAction rAction = rActionList.get(i);
					if (!labelAction.containsKey(rAction)) {
						labelAction.put(rAction, labelActionCount++);
					}
					String name = rAction.toString();
					if (name.contains("Grasp") || name.contains("UnGrasp")) {

						subGoalStates.add(rState);
					}
				}
				Integer currentLabel = labelState.get(rState);

				if (i == rStateList.size() - 1) {// This is a final node

					if (currentLabel == null) {
						finalStateCount.put(rState, 1);
					} else {
						finalStateCount.put(rState, finalStateCount.get(rState) + 1);
					}

				}

				if (currentLabel == null) {
					labelState.put(rState, labelStateCount);
					labelStateCount++;
				}
			}

		}

		//Determine the goal state;
		int maxCount = -1;
		ArrayList<RState> goals = new ArrayList<>();
		Set<RState> keySet = finalStateCount.keySet();
		for (RState rState : keySet) {
			int count = finalStateCount.get(rState);
			if(count > maxCount){
				goals.clear();
				goals.add(rState);
				maxCount = count;
			}
			else if(count == maxCount){
				goals.add(rState);
			}
		}
		Set<RState> keySet2 = labelState.keySet();
		//Set the goal reward for each goal state and sub goal states
		for (RState rState : keySet2) {
			if(goals.contains(rState)){
				rState.setReward(goalReward);
				rState.setGoal(true);
			}
			if(subGoalStates.contains(rState)){
				//rState.setReward(subGoalReward);
				rState.setSubGoal(true);
			}
			
		}

		//Q-Learning
		double[][] Q_Values = new double[labelState.size()][labelAction.size()];
		
		//For each episode
		for (int i = 0; i < demostrationsStates.size(); i++) {
			
			double previousQ = 0;
			ArrayList<RState> rStateList = demostrationsStates.get(i);
			ArrayList<RAction> rActionList = demostrationsActions.get(i);
			
			for (int j = rStateList.size()-2; j >= 0; j--) { //We start in the penultimate state
				RState St = rStateList.get(j);
				RAction At = rActionList.get(j);
				
				int row = labelState.get(St);
				int col = labelAction.get(At);
				Iterator<RState> set = labelState.keySet().iterator();
				RState cur;
				while(!(cur = set.next()).equals(rStateList.get(j+1)));
				RState nextSt = cur;
				int nextRow = labelState.get(nextSt);
				double reward = nextSt.getReward();
				double maxQ = 0.0;
				for (int k = 0; k < Q_Values[nextRow].length; k++) {
					maxQ = Math.max(maxQ, Q_Values[nextRow][k]);
				}
				//If nextState is a sub-goal then we should increment the Q value for the current state-action pair!!!!!!!!!!!!!!!!!!!!!!!!
				Q_Values[row][col] = Q_Values[row][col] + learningRate * (reward + discountFactor * maxQ - Q_Values[row][col]);
				if(nextSt.isSubGoal()&&j<rStateList.size()-2){
					double total = previousQ-Q_Values[row][col];
					double increasePercent = subGoalReward*total/100;
					Q_Values[row][col]+=increasePercent;
				}
				previousQ = Q_Values[row][col];
				String s = String.format("%.10f", Q_Values[row][col])+" ";
				System.out.print(s);
				
			}
			System.out.println();

		}
		/*for (int i = 0; i < Q_Values.length; i++) {
			String line = "";
			for (int j = 0; j < Q_Values[i].length; j++) {
				String s = String.format("%.6f", Q_Values[i][j]);
				line += s + ",";
			}
			System.out.println(line);
		}
		System.out.println();*/
	}

}
