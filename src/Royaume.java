public class Royaume {
	Case[][] listeCases = new Case[9][9];
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
			listeCases[Xref][Yref] = domino.getCaseRef();
			listeCases[Xrot][Yrot] = domino.getCaseRot();
			return true;
		} else
			return false;
		
		
	}

	public boolean checkPlacerDomino(Domino domino, int Xref, int Yref, int Xrot, int Yrot) {
		return true;
	}
}
