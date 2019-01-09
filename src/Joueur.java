import java.util.ArrayList;

public class Joueur {
	private String nom;
	private GameColor colorRoi;
	private ArrayList<Roi> listeRois= new ArrayList<>();
	private ArrayList<Move> nextMoves = new ArrayList<>();
	private Royaume royaume;
	private int score = 0;
	private boolean AI;
	
	

	public Joueur(String nom, GameColor colorRoi, boolean AI) {
		this.nom= nom;
		this.colorRoi=colorRoi;
		this.AI  = AI;
		this.royaume = new Royaume();
	}
	
	public void addRoi() {
		listeRois.add(new Roi(this.colorRoi));
	}
	
	public ArrayList<Roi> getListeRois() {
		return listeRois;
	}
	
	public void setListeRois(ArrayList<Roi> listeRois) {
		this.listeRois = listeRois;
	}
	
	public String getNom() {
		return nom;
	}
	
	public GameColor getColorRoi() {
		return colorRoi;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public Royaume getRoyaume() {
		return royaume;
	}
	
	public void addNextMove(Move move) {
		nextMoves.add(move);
	}
	
	public ArrayList<Move> getNextMoves() {
		return nextMoves;
	}
	
	public Move getMoveToDoForDomino(Domino domino) {
		for (Move move : nextMoves) {
			if (move.getDomino().equals(domino)) {
				return move;
			}
		}
		return null;
	}
	
	public boolean isAI() {
		return AI;
	}
	

	

}
