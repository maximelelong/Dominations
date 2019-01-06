
public class Main {

	public static void main(String[] args) {
		Royaume royaume = new Royaume();
		
		Case caseC1 = new Case(TypeTerrain.CHAMPS, 1);
		Case caseC2 = new Case(TypeTerrain.CHAMPS, 1);
		Case caseC3 = new Case(TypeTerrain.CHAMPS, 1);
		Case caseC4 = new Case(TypeTerrain.CHAMPS, 1);
		Case caseC5 = new Case(TypeTerrain.CHAMPS, 1);
		Case caseC6 = new Case(TypeTerrain.CHAMPS, 1);
		Case caseC7 = new Case(TypeTerrain.CHAMPS, 1);
		Case caseC8 = new Case(TypeTerrain.CHAMPS, 1);
		Case caseC9 = new Case(TypeTerrain.CHAMPS, 1);
		Case caseM1 = new Case(TypeTerrain.MINE, 3);
		Case caseM2 = new Case(TypeTerrain.MINE, 1);
		Case caseF1 = new Case(TypeTerrain.FORET, 2);
		Case caseF2 = new Case(TypeTerrain.FORET, 2);
		Case caseF3 = new Case(TypeTerrain.FORET, 2);
		Case caseF4 = new Case(TypeTerrain.FORET, 2);
		Case caseF5 = new Case(TypeTerrain.FORET, 2);
		Case caseF6 = new Case(TypeTerrain.FORET, 2);
		Case caseF7 = new Case(TypeTerrain.FORET, 2);
		Case caseF8 = new Case(TypeTerrain.FORET, 2);
		Case caseF9 = new Case(TypeTerrain.FORET, 2);
		Case caseF10 = new Case(TypeTerrain.FORET, 2);
		Case caseP1 = new Case(TypeTerrain.PRAIRIE, 0);
		Domino domino1 = new Domino(caseC1, caseF1, 20);
		Domino domino2 = new Domino(caseC2, caseF2, 20);
		Domino domino3 = new Domino(caseC3, caseF3, 20);
		Domino domino4 = new Domino(caseC4, caseF4, 20);
		Domino domino5 = new Domino(caseC5, caseF5, 20);
		Domino domino6 = new Domino(caseC6, caseF6, 20);
		Domino domino7 = new Domino(caseC7, caseF7, 20);
		Domino domino8 = new Domino(caseC8, caseF8, 20);
		Domino domino9 = new Domino(caseC9, caseF9, 20);
		Domino domino10 = new Domino(caseM1, caseF10, 20);
		Domino domino11 = new Domino(caseP1, caseM2, 18);
		royaume.placerDomino(domino1, 4, 3, Direction.HAUT);
		royaume.placerDomino(domino2, 5, 3, Direction.DROITE);
		royaume.placerDomino(domino3, 4, 5, Direction.BAS);
		royaume.placerDomino(domino4, 6, 6, Direction.GAUCHE);
		royaume.placerDomino(domino5, 7, 6, Direction.DROITE);
		royaume.placerDomino(domino6, 5, 4, Direction.BAS);
		royaume.placerDomino(domino7, 6, 4, Direction.BAS);
		royaume.placerDomino(domino8, 7, 4, Direction.BAS);
		royaume.placerDomino(domino9, 8, 4, Direction.HAUT);
		royaume.placerDomino(domino10, 7, 2, Direction.BAS);
		royaume.placerDomino(domino11, 5, 2, Direction.DROITE);
		
		
//		System.out.println(royaume);
//		System.out.println("");
//		System.out.println("Nombre de case dans le royaume : " + royaume.getCaseInRoyaume());
//		boolean isFull = royaume.isFull();
//		System.out.println("Royaume plein ? : " + isFull);
//		System.out.println("Score du royaume : " +royaume.calculerScore());
		
		Partie partie = new Partie(3);
		
		
//		String[] array = {royaume.toString(), royaume.toString(), royaume.toString(), royaume.toString()};
//		
//		String test = Outils.stringAside(5, array);
//		System.out.println(test);
//		System.out.println(royaume);
		
	}

}
