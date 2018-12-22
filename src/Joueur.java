import java.util.ArrayList;

public class Joueur {
	private String nom;
	private Color colorRoi;
	private ArrayList<Roi> listeRois= new ArrayList<>();
	

	public Joueur(String nom, Color colorRoi) {
		this.nom= nom;
		this.colorRoi=colorRoi;	
		
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
	public Color getColorRoi() {
		return colorRoi;
	}
	

}
