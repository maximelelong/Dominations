public class Royaume {
	static int largeurGrille = 9;
	static int hauteurGrille = 9;
	static int largeurMax = 5;
	static int hauteurMax = 5;
	
	Case[][] listeCases = new Case[largeurGrille][hauteurGrille];	

	public Royaume() {
		//On ajoute le chateau au mileu du royaume
		for(int x = 0; x < largeurGrille; x++) {
			for(int y = 0; y < hauteurGrille; y++) {
				listeCases[x][y] = new Case();
			}
		}
		listeCases[4][4] = new Case(TypeTerrain.CHATEAU, 0);
		
	}
	
	public boolean placerDomino(Domino domino, int Xref, int Yref, Direction dir) {
		int Xrot;
		int Yrot;
		if(dir.equals(Direction.HAUT)) {
			Xrot = Xref;
			Yrot = Yref - 1;
		} else if (dir.equals(Direction.BAS)) {
			Xrot = Xref;
			Yrot = Yref + 1;
		} else if (dir.equals(Direction.DROITE)) {
			Xrot = Xref + 1;
			Yrot = Yref;
		} else { //dir.equals(Direction.GAUCHE)
			Xrot = Xref - 1;
			Yrot = Yref;
		}
		
		
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
		for(int x = 0; x < largeurGrille; x++) {
			for(int y = 0; y < hauteurGrille; y++) {
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
		if(x < 0 || x > largeurGrille || y < 0 || y > hauteurGrille){
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
	
	public void printRoyaume() {
		for(int y = 0; y < hauteurGrille; y++) {
			for(int x = 0; x < largeurGrille; x++) {
				System.out.print(" -----");
			}
			System.out.print("\n");
			//print le type des cases de la ligne ligne	
			for(int x = 0; x < largeurGrille; x++) {
				if(x == 0)
					System.out.print("|");
				System.out.print(listeCases[x][y].getTypeTerrain() + "|");
			}
			System.out.print("\n");
			//print le nombre de couronnes des cases de la ligne
			for(int x = 0; x < largeurGrille; x++) {
				if(x == 0)
					System.out.print("|");
				System.out.print(listeCases[x][y].printCouronnes() + "|");
			}
			System.out.print("\n");
			
			if (y == hauteurGrille -1 ) {
				for(int x = 0; x < largeurGrille; x++) {
					System.out.print(" -----");
				}
			}
		}
	}
	
	
	
	
	
	
	
	
}
