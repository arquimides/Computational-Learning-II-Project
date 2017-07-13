package model;

import java.util.ArrayList;

public class RAction implements Comparable<RAction>{

	private ArrayList<RSubAction> subActions;
	
	public RAction() {
		super();
		subActions = new ArrayList<>();
	}


	@Override
	public boolean equals(Object obj) {
		
	 return this.toString().equals(obj.toString());
	}

	public ArrayList<RSubAction> getSubActions() {
		return subActions;
	}


	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}


	public void setSubActions(ArrayList<RSubAction> subActions) {
		this.subActions = subActions;
	}
	
	public void addRSubState(RSubAction rsa){
		subActions.add(rsa);
	}


	@Override
	public String toString() {
		String resp = "(";
		for (int i = 0; i < subActions.size(); i++) {
			resp+=subActions.get(i).toString()+",";
		}
		resp = resp.substring(0,resp.length()-1);
		resp +=")";
		return resp;
	}


	@Override
	public int compareTo(RAction o) {
		return subActions.toString().compareTo(o.getSubActions().toString());
	}
	
	
}
