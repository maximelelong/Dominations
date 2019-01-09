import java.awt.Color;

public class GUI {
	public static Color boardBackgroundColor = Color.LIGHT_GRAY;
	public static int tileSize = 50;
	public static int royaumeSize = 9*tileSize;
	public static int spaceBetweenRoyaumes = 20;
	
	public static int sizeX = 1500;
	public static int sizeY = 3*spaceBetweenRoyaumes + 2*royaumeSize;
	
	public static int XDominosAJouer = 800;
	
	public static void initGUI() {
		
		StdDraw.setCanvasSize(sizeX, sizeY);
		StdDraw.setXscale(0, sizeX);
		StdDraw.setYscale(0, sizeY);
		StdDraw.enableDoubleBuffering();
	}
	
	public static void refreshBoard(String message) {
		
		StdDraw.clear(boardBackgroundColor);
		
		//afficher les royaumes
		for (Joueur joueur : Partie.listeJoueurs) {
			
			int XStartRoyaume;
			int YStartRoyaume;
			Royaume royaume = joueur.getRoyaume();
			
			switch (joueur.getColorRoi()) {
			case ROUGE:
				XStartRoyaume = spaceBetweenRoyaumes;
				YStartRoyaume = spaceBetweenRoyaumes*2 + royaumeSize;
				break;
			case VERT:
				XStartRoyaume = spaceBetweenRoyaumes*2 + royaumeSize;
				YStartRoyaume = spaceBetweenRoyaumes*2 + royaumeSize;
				break;
			case BLEU:
				XStartRoyaume = spaceBetweenRoyaumes;
				YStartRoyaume = spaceBetweenRoyaumes;
				break;
			case ROSE:
				XStartRoyaume = spaceBetweenRoyaumes*2 + royaumeSize;
				YStartRoyaume = spaceBetweenRoyaumes;
				break;
			default:
				throw new NullPointerException("La couleur de ce joueur est inconnue");
			}
			
			for (int x = 0; x < Royaume.largeurGrille; x++) {
				for (int y = 0; y < Royaume.hauteurGrille; y++) {
					int XBoard = XStartRoyaume + x*tileSize;
					int YBoard = YStartRoyaume + y*tileSize;
					Case caseCourante = royaume.getCase(x, y);
					printCase(caseCourante, joueur, XBoard, YBoard);				
				}
			}
		}
		
		StdDraw.show();
	}
	
	public static void printCase(Case caseToPrint,Joueur joueur, int x, int y) {
		TypeTerrain type = caseToPrint.getTypeTerrain();
		if (type.equals(TypeTerrain.CHATEAU)) {
			StdDraw.setPenColor(joueur.getColorRoi().getAwtColor());
			StdDraw.filledSquare(x, y, tileSize/3);
		} else {
			StdDraw.setPenColor(type.getColor());
			StdDraw.filledSquare(x, y, tileSize/2);
			StdDraw.setFont();
			StdDraw.setPenColor();
			StdDraw.text(x, y+10, type.toString());
			if (caseToPrint.getNbCouronnes() > 0) {
				StdDraw.text(x, y-10, String.valueOf(caseToPrint.getNbCouronnes()));
			}
		}
	}
	
	public static void refreshBoard() {
		refreshBoard("");
	}

}
