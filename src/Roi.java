
public class Roi {
	private Royaume royaume ;
	private Color color; 
	private Domino currentDomino; 

	
	public Roi(Color color) {
		this.color=color;
		this.royaume= new Royaume();
	}
	
	public void placerSurDomino(Domino domino) {
		currentDomino = domino;
		
	}
	public Royaume getRoyaume() {
		return royaume;
	}
	public Color getColor() {
		return color;
	}
	public Domino getCurrentDomino() {
		return currentDomino;
	}
	

}
