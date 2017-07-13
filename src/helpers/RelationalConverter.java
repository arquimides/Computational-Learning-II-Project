package helpers;

import java.util.ArrayList;

import model.Demonstration;
import model.RAction;
import model.RState;
import model.RSubAction;
import model.RSubState;
import model.Trace;

public class RelationalConverter {
	
	static boolean grasped = false; //Initialy we asume that the arm is not grasping any object

	public static ArrayList<RState> convertToRState(Demonstration d, String[] x_RELATIVE, String[] y_RELATIVE,
			String[] values) {
		ArrayList<RState> resp = new ArrayList<>();
		for (int i = 0; i < d.getTraces().get(0).getPositions().size(); i++) {
			RState iRS = getRState(d, i);
			resp.add(iRS);
		}
		return resp;
	}

	private static RState getRState(Demonstration d, int time) {

		RState resp = new RState();
		int objects = d.getTraces().size();

		for (int i = 0; i < objects; i++) {

			Trace o1 = d.getTraces().get(i);
			int x1 = o1.getPositions().get(time).getX();
			int y1 = o1.getPositions().get(time).getY();

			for (int j = i + 1; j < objects; j++) {

				Trace o2 = d.getTraces().get(j);
				int x2 = o2.getPositions().get(time).getX();
				int y2 = o2.getPositions().get(time).getY();

				String X = getXRelative(x1, x2);
				String xV = getRValue(x1, x2);
				String Y = getYRelative(y1, y2);
				String yV = getRValue(y1, y2);

				RSubState rss = new RSubState(X, xV, Y, yV);
				resp.addRSubState(rss);
			}

		}

		return resp;
	}

	private static String getRValue(int v1, int v2) {
		int abs = Math.abs(v1 - v2);
		if (abs == 0) {
			return "-";
		}
		if (abs >= 1 && abs <= 2) {
			return "C";
		}
		if (abs >= 3 && abs <= 5) {
			return "M";
		}
		if (abs >= 6) {
			return "F";
		}
		return null;
	}

	private static String getYRelative(int y1, int y2) {
		if (y1 > y2) {
			return "U";
		}
		if (y1 < y2) {
			return "D";
		}
		return "-";
	}

	private static String getXRelative(int x1, int x2) {
		if (x1 > x2) {
			return "R";
		}
		if (x1 < x2) {
			return "L";
		}
		return "-";
	}

	public static void generateStateActions(ArrayList<RState> all_states, Demonstration d, ArrayList<RState> r_states,
			ArrayList<RAction> r_actions) {

		RState previous = all_states.get(0);
		r_states.add(previous);

		RAction action = new RAction();
		
		
		for (int i = 1; i < all_states.size(); i++) {
			RState current = all_states.get(i);

			ArrayList<String> moves = getMove(d, i-1, i);
			for (int j = 0; j < moves.size(); j++) {
				RSubAction rsa = new RSubAction(moves.get(j));
				action.addRSubState(rsa);
			}

			if (previous.compareTo(current)!=0) {
				r_states.add(current);
				r_actions.add(action);
				action = new RAction();
			}
			previous = current;
		}

		grasped = false; //Reset grasped
	}

	private static ArrayList<String> getMove(Demonstration d, int i, int j) {
		ArrayList<String> resp = new ArrayList<>();

		int x_i_arm = d.getTraces().get(0).getPositions().get(i).getX();
		int y_i_arm = d.getTraces().get(0).getPositions().get(i).getY();

		int x_j_arm = d.getTraces().get(0).getPositions().get(j).getX();
		int y_j_arm = d.getTraces().get(0).getPositions().get(j).getY();

		for (int k = 1; k < d.getTraces().size(); k++) {
			int x_i_o = d.getTraces().get(k).getPositions().get(i).getX();
			int y_i_o = d.getTraces().get(k).getPositions().get(i).getY();
			
			int x_j_o = d.getTraces().get(k).getPositions().get(j).getX();
			int y_j_o = d.getTraces().get(k).getPositions().get(j).getY();
			
			if(!grasped && x_i_arm == x_i_o && y_i_arm == y_i_o && x_j_arm == x_j_o && y_j_arm == y_j_o){
				grasped = true;
				resp.add("Grasp");
				break;
			}
			if(grasped && x_i_arm == x_i_o && y_i_arm == y_i_o && (x_j_arm != x_j_o || y_j_arm != y_j_o)){
				grasped = false;
				resp.add("UnGrasp");
				break;
			}
		}
		
		//Arm move
		int degrees = (int) (Math.toDegrees(Math.atan2(y_j_arm - y_i_arm, x_j_arm - x_i_arm)));
		String move = ""+degrees;
		resp.add(move);
		
		return resp;
	}

}