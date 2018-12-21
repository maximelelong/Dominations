public class Royaume {
	Case[][] listeCases = new Case[9][9];	
	static int largeurMax = 5;
	static int hauteurMax = 5;
	
	public Royaume() {
		//On ajoute le chateau au mileu du royaume
		listeCases[5][5] = new Case(TypeTerrain.CHATEAU, 0);
	}
	public void afficherRoyaume() {
		
	}
	
	public boolean placerDomino(Domino domino) {
		// captation des données
		int Xref = 0;
		int Yref = 0;
		int Xrot = 0;
		int Yrot = 0;
		
		if (canPlace(domino, Xref, Yref, Xrot, Yrot)) {
			listeCases[Xref][Yref] = domino.getCaseRef();
			listeCases[Xrot][Yrot] = domino.getCaseRot();
			return true;
		} else
			return false;
		
		
	}
	/**Attention methode fortement dégueulasse
	 * 
	 * @param domino
	 * @param Xref
	 * @param Yref
	 * @param Xrot
	 * @param Yrot
	 * @return true si le domino peut être placé à cet emplacement 
	 */
	public boolean canPlace(Domino domino, int Xref, int Yref, int Xrot, int Yrot) {
		
		//On vérifie que les coordonnées voulues sont dans la grille
		if(!isInGrid(Xref, Yref) || !isInGrid(Xrot, Yrot)){
						return false;
		}
		
		//On vérifie que l'emplacement ciblé n'est pas déja occupé
		boolean emplacementVide = !listeCases[Xref][Yref].isEmpty() || !listeCases[Xrot][Yrot].isEmpty();
		if (emplacementVide)
			return false;
		
		/*Ajoute le domino pour tester si cela dépasse la taille max du royaume
		 * Teste si c'est le cas puis supprime le domino
		 */
		listeCases[Xref][Yref] = domino.getCaseRef();
		listeCases[Xrot][Yrot] = domino.getCaseRot();
		boolean isFull = this.isFull();
		listeCases[Xref][Yref] = new Case();
		listeCases[Xrot][Yrot] = new Case();
		
		if(isFull) {
			return false;
		} else {
						
			int[][] coordCible = new int[][] {{Xref,Yref},{Xrot,Yrot}};
			Case[]  casesDominoAPlacer = new Case[] {domino.getCaseRef(),domino.getCaseRot()};
			
			for (int i = 0; i<coordCible.length; i++){
				int[] coord = coordCible[i];
				
				//pas les bonnes coordonnées
				for(int[] supp : new int[][] {{1,0},{-1,0},{0,1},{0,-1}}) {
						
					int j = supp[0];
					int k =supp[1];
					
					int[] coordAdjacent = new int[] {coord[0]+j, coord[1]+k};
					Case caseAdjacente = listeCases[coordAdjacent[0]][coordAdjacent[1]];
					
					//On vérifie qu'on ne teste pas l'autre case du domino
					if(!coordAdjacent.equals(coordCible[(i==0 ? 1 : 0)])) {
						
						//On vérifie que la case adjacente n'est pas vide
						if (!caseAdjacente.isEmpty()) {
							
							//On vérifie si la case adjacente a le même terrain que la case du domino
							//ou si elle est adjacente au chateau
							if (caseAdjacente.getTypeTerrain().equals(casesDominoAPlacer[i].getTypeTerrain())
									||caseAdjacente.getTypeTerrain().equals(TypeTerrain.CHATEAU)) {
								return true;
							}
						}
					}
					
				}
			}		
			return false;
		}
	}
	
	public boolean isFull() {
		int xMin =  9;
		int xMax = 0;
		int yMin = 9;
		int yMax = 0;
		for(int x = 0; x < 9; x++) {
			for(int y = 0; y < 9; y++) {
				if (!listeCases[x][y].isEmpty()) {
					if (x>xMax)
						xMax = x;
					if(x<xMin)
						xMin = x;
					if(y>yMax)
						yMax = y;
					if (y<yMin)
						yMin = y;
				}
			}
		}
		if (xMax-xMin > largeurMax || yMax-yMin > hauteurMax) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isInGrid(int x, int y) {
		if(x < 0 || x > 9 || y < 0 || y > 9){
			return false;
		} else 
			return true;
	}
	
	public Case getCase(int x, int y) {
		if(isInGrid(x, y)) {
			return listeCases[x][y];
		} else 
			return null;
	}
}
