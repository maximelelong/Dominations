
public class Roi {
	private Royaume royaume ;
	private Color color; 
	

	
	public Roi(Color color) {
		this.color=color;
		this.royaume= new Royaume();
	}
	
	public Royaume getRoyaume() {
		return royaume;
	}
	public Color getColor() {
		return color;
	}

	

}
