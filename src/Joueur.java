import java.util.ArrayList;

public class Joueur {
	private String nom;
	private Color colorRoi;
	private ArrayList<Roi> listeRois= new ArrayList<>();
	private int score = 0;
	private boolean AI;
	

	public Joueur(String nom, Color colorRoi, boolean AI) {
		this.nom= nom;
		this.colorRoi=colorRoi;
		this.AI  = AI;
	}
	
	public void addRoi() {
		listeRois.add(new Roi(this.colorRoi, this.AI));
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
	
	public Color getColorRoi() {
		return colorRoi;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	

	

}
