
public class Roi {
	private Royaume royaume ;
	private Color color;
	private boolean AI;
	
	public Roi(Color color, boolean AI) {
		this.color=color;
		this.royaume= new Royaume();
		this.AI = AI;
	}
	
	public Royaume getRoyaume() {
		return royaume;
	}
	public Color getColor() {
		return color;
	}
	
	public boolean isAI() {
		return AI;
	}

	

}
