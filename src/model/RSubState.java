package model;

public class RSubState implements Comparable<RSubState>{
	private String X;
	private String xV;
	private String Y;
	private String yV;
	
	public RSubState(String x, String xV, String y, String yV) {
		super();
		X = x;
		this.xV = xV;
		Y = y;
		this.yV = yV;
	}

	@Override
	public boolean equals(Object obj) {
		
	 return this.toString().equals(obj.toString());
	}
	public String getX() {
		return X;
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	public void setX(String x) {
		X = x;
	}

	public String getxV() {
		return xV;
	}

	public void setxV(String xV) {
		this.xV = xV;
	}

	public String getY() {
		return Y;
	}

	public void setY(String y) {
		Y = y;
	}

	public String getyV() {
		return yV;
	}

	public void setyV(String yV) {
		this.yV = yV;
	}

	@Override
	public String toString() {
		
		return "("+X+","+xV+","+Y+","+yV+")";
	}

	@Override
	public int compareTo(RSubState o) {
		if(X.equals(o.X) && xV.equals(o.xV) && Y.equals(o.Y) && yV.equals(o.yV) )
			return 0;
		return -1;
	}
	
	
}
