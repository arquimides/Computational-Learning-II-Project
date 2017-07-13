package model;

import java.util.ArrayList;

public class Demonstration {
	public String demonstrationId;
	public ArrayList<Trace> traces;
	
	public Demonstration(String demonstrationId) {
		super();
		this.demonstrationId = demonstrationId;
		traces = new ArrayList<>();
	}
	public String getDemonstrationId() {
		return demonstrationId;
	}
	public void setDemonstrationId(String demonstrationId) {
		this.demonstrationId = demonstrationId;
	}
	public ArrayList<Trace> getTraces() {
		return traces;
	}
	public void setTraces(ArrayList<Trace> traces) {
		this.traces = traces;
	}
	
	public void addTrace (Trace t){
		traces.add(t);
	}
}
