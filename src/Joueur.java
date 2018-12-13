import java.util.ArrayList;

public class Joueur {
	String nom;
	Color colorRoi;
	ArrayList<Roi> listeRois= new ArrayList<>();
	

	public Joueur(String nom, Color colorRoi) {
		this.nom= nom;
		this.colorRoi=colorRoi;	
		
	}
	public void addRoi() {
		listeRois.add(new Roi(this.colorRoi));
		
	}
	

}
