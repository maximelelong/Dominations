
public class Main {

	public static void main(String[] args) {
		Royaume royaume = new Royaume();
		
		Case case1 = new Case(TypeTerrain.CHAMPS, 1);
		Case case2 = new Case(TypeTerrain.FORET, 2);
		Domino domino1 = new Domino(case1, case2, 20);
		royaume.placerDomino(domino1, 4, 3, Direction.HAUT);
		royaume.placerDomino(domino1, 5, 3, Direction.DROITE);
		royaume.placerDomino(domino1, 4, 5, Direction.BAS);
		royaume.placerDomino(domino1, 6, 6, Direction.GAUCHE);
		royaume.placerDomino(domino1, 7, 6, Direction.DROITE);
		
		
		royaume.printRoyaume();
		
	}

}
