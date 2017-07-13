package model;
import java.util.ArrayList;

public class Trace {
	private String objectId;
	private ArrayList<Position2D> positions;
	
	public Trace(String objectId) {
		super();
		this.objectId = objectId;
		positions = new ArrayList<>();
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public ArrayList<Position2D> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Position2D> positions) {
		this.positions = positions;
	}
	
	public void addPosition(Position2D p){
		positions.add(p);
	}
}
