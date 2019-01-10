import java.awt.Color;

public class GUI {
	public static Color boardBackgroundColor = Color.LIGHT_GRAY;
	public static int tileSize = 50;
	public static int royaumeSize = 9*tileSize;
	public static int spaceBetweenRoyaumes = 20;
	
	public static int sizeX = 1300;
	public static int sizeY = 3*spaceBetweenRoyaumes + 2*royaumeSize;
	
	public static int XDominosAJouer = 3*spaceBetweenRoyaumes + 2*royaumeSize + 2*spaceBetweenRoyaumes ;
	public static int XDominosAChoisir = XDominosAJouer + 2*tileSize + 2*spaceBetweenRoyaumes;
	public static int YStartListesDominos = 600;
	
	public static void initGUI() {
		
		StdDraw.setCanvasSize(sizeX, sizeY);
		StdDraw.setXscale(0, sizeX);
		StdDraw.setYscale(0, sizeY);
		StdDraw.enableDoubleBuffering();
	}
	public static void choosePlaceForDomino(Joueur joueur, Domino domino) {
		Royaume royaume = joueur.getRoyaume();
		Direction dir = Direction.GAUCHE;
		int XStartRoyaume = getRoyaumeXStart(joueur);
		int YStartRoyaume = getRoyaumeYStart(joueur);
		while (true) {
			if (StdDraw.isMousePressed()) {
				
				int x = (int) StdDraw.mouseX();
				int y = (int) StdDraw.mouseY();
				if (x > XStartRoyaume && x < XStartRoyaume + royaumeSize &&
						y > YStartRoyaume && y < YStartRoyaume + royaumeSize) {
					int xCoord = (x - XStartRoyaume)/tileSize;//division entière
					int yCoord = (y - YStartRoyaume)/tileSize;
					System.out.println("("+xCoord +","+yCoord+")");
					
					if (royaume.placerDomino(new Move(domino, xCoord, yCoord, dir))) {
						break;
					}
				}
			}else {
				//TODO ajouter la possibilité de tourner le domino
			}
		}
	}
	
	public static int choisirDomino(Joueur joueur) {
		while (true) {
			if (StdDraw.isMousePressed()) {
				
				int x = (int) StdDraw.mouseX();
				int y = (int) StdDraw.mouseY();
				
				int indiceDomino = -1;
				if (x > XDominosAChoisir && x < XDominosAChoisir + tileSize*2) {
					for (int i = 0; i < 4; i++) {
						if (y < YStartListesDominos - i*(spaceBetweenRoyaumes + tileSize) &&
								y > YStartListesDominos - (i+1)*(spaceBetweenRoyaumes + tileSize)) {
							indiceDomino = i;
							break;
						}
					}
				}
				
				if (indiceDomino != -1) {
					if (!Partie.dominosAChoisir.get(indiceDomino).isChoosed()) {
						return indiceDomino;
					}
				}
			}
		}
	}
	
	public static void loadBoard(String message) {
		StdDraw.clear(boardBackgroundColor);

		//afficher les royaumes
		for (Joueur joueur : Partie.listeJoueurs) {

			int XStartRoyaume = getRoyaumeXStart(joueur);
			int YStartRoyaume = getRoyaumeYStart(joueur);
			Royaume royaume = joueur.getRoyaume();

			for (int x = 0; x < Royaume.largeurGrille; x++) {
				for (int y = 0; y < Royaume.hauteurGrille; y++) {
					int XBoard = XStartRoyaume + x*tileSize + tileSize/2;
					int YBoard = YStartRoyaume + y*tileSize + tileSize/2;
					Case caseCourante = royaume.getCase(x, y);
					if (caseCourante.isCastle()) {
						printChateau(joueur.getColorRoi().getAwtColor(), XBoard, YBoard);
					} else {
						printCase(caseCourante, XBoard, YBoard);
					}
				}
			}

		}
		//Affiche la liste des dominos à jouer
		for (int i = 0; i < Partie.dominosAjouer.size(); i++) {
			Domino domino = Partie.dominosAjouer.get(i);
			Case caseRef = domino.getCaseRef();
			Case caseRot = domino.getCaseRot();
			int yDomino = YStartListesDominos - tileSize/2 - i*(spaceBetweenRoyaumes+tileSize);
			printCase(caseRef, XDominosAJouer + tileSize/2, yDomino);
			printCase(caseRot, XDominosAJouer + 3*tileSize/2, yDomino);
			//Affiche un pion de la couleur du joueur qui l'a choisi
			if (domino.isChoosed()) {
				Color couleurRoi = domino.getRoi().getColor().getAwtColor();
				StdDraw.setPenColor(couleurRoi);
				StdDraw.setPenRadius();
				StdDraw.filledCircle(XDominosAJouer + tileSize, yDomino, tileSize/3);
			}

		}

		//Affiche la liste des dominos à jouer
		for (int i = 0; i < Partie.dominosAChoisir.size(); i++) {
			Domino domino = Partie.dominosAChoisir.get(i);
			Case caseRef = domino.getCaseRef();
			Case caseRot = domino.getCaseRot();
			int yDomino = YStartListesDominos - tileSize/2 - i*(spaceBetweenRoyaumes+tileSize);
			printCase(caseRef, XDominosAChoisir + tileSize/2, yDomino);
			printCase(caseRot, XDominosAChoisir + 3*tileSize/2, yDomino);
			//Affiche un pion de la couleur du joueur qui l'a choisi
			if (domino.isChoosed()) {
				Color couleurRoi = domino.getRoi().getColor().getAwtColor();
				StdDraw.setPenColor(couleurRoi);
				StdDraw.setPenRadius();
				StdDraw.filledCircle(XDominosAChoisir + tileSize, yDomino, tileSize/3);
			}
		}
	}
	
	public static void refreshBoard(String message) {
		loadBoard(message);
		StdDraw.show();
	}
	
	public static void printCase(Case caseToPrint, int x, int y) {
		TypeTerrain type = caseToPrint.getTypeTerrain();
		StdDraw.setPenColor(type.getColor());
		StdDraw.filledSquare(x, y, tileSize/2);
		StdDraw.setFont();
		StdDraw.setPenColor();
		StdDraw.text(x, y+10, type.toString());
		if (caseToPrint.getNbCouronnes() > 0) {
			StdDraw.text(x, y-10, String.valueOf(caseToPrint.getNbCouronnes()));
		}
	} 
	
	
	public static void printChateau(Color color, int x, int y) {
		StdDraw.setPenColor(color);
		StdDraw.filledSquare(x, y, tileSize/3);
	}
	
	public static void refreshBoard() {
		refreshBoard("");
	}
	
	public static int getRoyaumeXStart(Joueur joueur) {
		
		int XStartRoyaume;

		switch (joueur.getColorRoi()) {
		case ROUGE:
			XStartRoyaume = spaceBetweenRoyaumes;
			break;
		case VERT:
			XStartRoyaume = spaceBetweenRoyaumes*2 + royaumeSize;
			break;
		case BLEU:
			XStartRoyaume = spaceBetweenRoyaumes;
			break;
		case ROSE:
			XStartRoyaume = spaceBetweenRoyaumes*2 + royaumeSize;
			break;
		default:
			throw new NullPointerException("La couleur de ce joueur est inconnue");
		}
		
		return XStartRoyaume;
	}
	
	public static int getRoyaumeYStart(Joueur joueur) {
		int YStartRoyaume;

		switch (joueur.getColorRoi()) {
		case ROUGE:
			YStartRoyaume = spaceBetweenRoyaumes*2 + royaumeSize;
			break;
		case VERT:
			YStartRoyaume = spaceBetweenRoyaumes*2 + royaumeSize;
			break;
		case BLEU:
			YStartRoyaume = spaceBetweenRoyaumes;
			break;
		case ROSE:
			YStartRoyaume = spaceBetweenRoyaumes;
			break;
		default:
			throw new NullPointerException("La couleur de ce joueur est inconnue");
		}
		return YStartRoyaume;
	}

}
