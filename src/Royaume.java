import java.util.ArrayList;

public class Royaume {
	ArrayList<Case> listeCases = new ArrayList<>();
	public Royaume() {
		
	}
	public void afficherRoyaume() {
		
	}
	
	public boolean placerDomino(Domino domino) {
		int Xref = 0;
		int Yref = 0;
		int Xrot = 0;
		int Yrot = 0;
		
		// captation des donnĂ©es
		
		if (checkPlacerDomino(domino, Xref, Yref, Xrot, Yrot)) {
			listeCases.add(domino.getCaseRef());
			listeCases.add(domino.getCaseRot());
			return true;
		} else
			return false;
		
		
	}

	public boolean checkPlacerDomino(Domino domino, int Xref, int Yref, int Xrot, int Yrot) {
		return true;
	}
}
