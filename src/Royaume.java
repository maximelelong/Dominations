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
		
		// captation des données
		
		if (canPlace(domino, Xref, Yref, Xrot, Yrot)) {
			listeCases[Xref][Yref] = domino.getCaseRef();
			listeCases[Xrot][Yrot] = domino.getCaseRot();
			return true;
		} else
			return false;
		
		
	}

	public boolean canPlace(Domino domino, int Xref, int Yref, int Xrot, int Yrot) {
			
		//On vérifie que l'emplacement ciblé n'est pas déja occupé
		boolean emplacementVide = listeCases[Xref][Yref] == null || listeCases[Xrot][Yrot] == null;
		if (emplacementVide) {
			return false;
		}
		
		int[][] coordCible = new int[][] {{Xref,Yref},{Xrot,Yrot}};
		Case[]  casesDominoAPlacer = new Case[] {domino.getCaseRef(),domino.getCaseRot()};
		
		for (int i = 0; i<coordCible.length; i++){
			int[] coord = coordCible[i];
					
			for(int j : new int[] {1,-1}) {
				for(int k : new int[] {1,-1}) {
					
					int[] coordAdjacent = new int[] {coord[0]+j,coord[1]+k};
					Case caseAdjacente = listeCases[coordAdjacent[0]][coordAdjacent[1]];
					
					//On vérifie qu'on ne teste pas l'autre case du domino
					if(!coordAdjacent.equals(coordCible[(i==0 ? 1 : 0)])) {
						
						//On vérifie que la case adjacente n'est pas vide
						if (caseAdjacente != null) {
							
							//On vérifie si la case adjacente a le même terrain que la case du domino
							if (caseAdjacente.getTypeTerrain().equals(casesDominoAPlacer[i].getTypeTerrain())) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
}
