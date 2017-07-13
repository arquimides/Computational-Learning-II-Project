package model;

public class RSubAction implements Comparable<RSubAction>{

	private String move;
	
	public RSubAction(String move) {
		super();
		this.move = move;
	}
	@Override
	public boolean equals(Object obj) {
		
	 return this.toString().equals(obj.toString());
	}


	@Override
	public String toString() {

		return move;
	}


	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}


	@Override
	public int compareTo(RSubAction o) {
		if(move.equals(o.move))
			return 0;
		return -1;
	}

}
