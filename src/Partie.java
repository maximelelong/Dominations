import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


public class Partie {
	static Scanner scan = new Scanner(System.in); 
	static ArrayList<GameColor> couleursRestantes=new ArrayList<>();
	static ArrayList<Domino> dominosPioche =new ArrayList<>();
	static ArrayList<Joueur> listeJoueurs=new ArrayList<>();
	static ArrayList<Domino> dominosAChoisir= new ArrayList<>();
	static ArrayList<Domino> dominosAjouer = new ArrayList<Domino>();
	static ArrayList<Roi> listeRois = new ArrayList<Roi>();
	static Roi currentRoi;
	
	Random rand = new Random();

	public Partie(int nbJoueur) {
		GUI.initGUI();
		
		//import des dominos depuis le fichier csv
		importDominos();
		
		// on prend une liste avec les couleurs disponibles � attribuer 
		couleursRestantes.addAll(Arrays.asList(GameColor.values()));
//		for(int i=0;i<nbJoueur;i++ ) {
//			ajouterJoueur(i);
//		}
		
		for(int i=0;i<nbJoueur-1;i++ ) {
			ajouterIA();
		}
		GameColor couleur = couleursRestantes.get(0);
		listeJoueurs.add(new Joueur("J1", couleur, false));
		couleursRestantes.remove(0);
		
		
		//Attribution des rois
		
		//s'il y a 2 joueurs => 2 rois par joueur
		if(nbJoueur == 2) {
			removeDominosFromPioche(24);
			for(int i = 0; i<nbJoueur ; i++) {
				listeJoueurs.get(i).addRoi();
				listeJoueurs.get(i).addRoi();
			}
		//sinon => 1 roi par joueur
		} else {
			if (nbJoueur == 3)
				removeDominosFromPioche(12);
			for(int i = 0; i<nbJoueur ; i++) {
				listeJoueurs.get(i).addRoi();
			}
		}
		
		for (Joueur joueur : listeJoueurs) {
			listeRois.addAll(joueur.getListeRois());
		}
		
		
		
		premierTour();
		
		
		boolean royaumesAllFull = false;
		while (dominosPioche.size() >= listeRois.size() && !royaumesAllFull) {
			
			royaumesAllFull = true;
			//v�rifie si au moins un des royaume n'est pas plein => il peut encore jouer => nouveau tour
			for (Roi roi : listeRois) {
				if (!getJoueurOfRoi(roi).getRoyaume().isFull()) {
					royaumesAllFull = false;
					break;
				}
			}
			
			nouveauTour();
			
		}
		
		//Calcul du score de chaque joueur
		for (Joueur joueur : listeJoueurs) {
			int score = joueur.getRoyaume().calculerScore();
			joueur.setScore(score);
		}
		
		//On ordonne les joueurs selon leur score (dans l'ordre d�croissant)
		listeJoueurs.sort(Comparator.comparing(Joueur::getScore).reversed());
		
		System.out.println("");
		System.out.println(Outils.stringInFrame("R�sultats",5));
		System.out.println("");
		for(int i = 0; i < listeJoueurs.size(); i++) {
			Joueur joueur = listeJoueurs.get(i);
			System.out.println((i+1) + "e : " + joueur.getNom() + " (" + joueur.getColorRoi() +") avec " + joueur.getScore());
		}
	}
	
	public void nouveauTour() {
		
//		System.out.println(Outils.stringInFrame("==================== Nouveau tour ===================="));
//		System.out.println("Dominos restants dans la pioche : " + dominosPioche.size());
//		System.out.println("");
		
		dominosAjouer.addAll(dominosAChoisir);
		dominosAChoisir.clear();
		tirerNouveauxDominos();	
		
		int i = dominosAjouer.size() - 1;
		while(i >= 0) {
			Domino dominoAJouer = dominosAjouer.get(0);
			
			Roi roi = dominoAJouer.getRoi();
			currentRoi = roi;
			
			//le joueur choisi un domino pour le prochain tour
			GUI.refreshBoard();
			choisirDomino(roi);
			dominoAJouer.removeRoi();			
			
			//le joueur choisit ce qu'il fait du domino choisi au tour pr�c�dent
			GUI.refreshBoard();
			if (getJoueurOfRoi(roi).getRoyaume().isFull()) {
				System.out.println("Le royaume du roi " + roi.getColor() + " est plein");
				System.out.println("Le domino � jouer est d�fauss�");
			} else {
				demandeOuPlacerDomino(dominoAJouer, roi);
			}
			i -= 1;
		}
	}
	
	
	
	public void tirerNouveauxDominos() {
		for (int i = 0; i < listeRois.size(); i++) {
			int randomIndex = rand.nextInt(dominosPioche.size());
			Domino domino = dominosPioche.get(randomIndex);
			dominosPioche.remove(domino);
			dominosAChoisir.add(domino);
		}
		
		//Permet d'ordonner les dominos selon leur nombre
		dominosAChoisir.sort(Comparator.comparing(Domino::getNumero));
	}
	
	public String listeDominosToString(ArrayList<Domino> listeDomino) {
		String string = "";
		for (int i = 0; i < listeDomino.size(); i++) {
			Domino domino = listeDomino.get(i);
			String colorRoi = "";
			if (domino.isChoosed()) {
				colorRoi = " \nRoi\n" + domino.getRoi().getColor();
			}
			string = string + Outils.stringAside(1, "\n(" + (i+1) + ")",domino.toString(),colorRoi);
			
			string += "\n";
		}
		return string;
	}	
	
	private void choisirDomino(Roi roi) {
		
		/*
		 * Dans cette m�thode, tous les indices affich�s ont un "+1"
		 * pour compter � partir de 1 pour l'utilisateur et pas � partir de 0
		 * Inversement les indices entr�s par l'utilisateur sont d�crement�s de 1	
		 */
		
		int rangDomino;
		Joueur joueur = getJoueurOfRoi(roi);
		
		if (joueur.isAI()) {
			
			
			ArrayList<Domino> dominosDispo = new ArrayList<>();
			for (Domino domino : dominosAChoisir) {
				if (!domino.isChoosed()) {
					dominosDispo.add(domino);
				}
			}
			
			Domino dominoChoisi = IA.choisirBestDomino(joueur, dominosDispo);
			rangDomino = dominosAChoisir.indexOf(dominoChoisi);
			
			printAfterRefresh("A choisi le " + (rangDomino+1) + "e domino");
			
		} else {			
			GUI.refreshBoard();
			printAfterRefresh("Quel domino choisissez-vous pour le prochain tour ? \n");
//			rangDomino = -1;
//			
//			while(rangDomino < 0 || rangDomino >= dominosAChoisir.size()) {
//				try {
//					rangDomino = Integer.valueOf(scan.nextLine()) - 1;
//					if(dominosAChoisir.get(rangDomino).isChoosed()) {
//						printAfterRefresh("Ce domino a d�j� �t� choisi, veuillez recommencer");
//						rangDomino = -1;
//					}
//				} catch (Exception e) {
//					printAfterRefresh("Choix invalide, veuillez recommencer :");
//				}
//			}
			rangDomino = GUI.choisirDomino(joueur);
		}
		
		Domino dominoChoisi = dominosAChoisir.get(rangDomino);
		dominoChoisi.setRoi(roi);
	}

	private void demandeOuPlacerDomino(Domino domino, Roi roi) {
		Joueur joueur = getJoueurOfRoi(roi);
		Royaume royaume = joueur.getRoyaume();
		
		if (joueur.isAI()) {
			Move moveToDo = joueur.getMoveToDoForDomino(domino);
			
			if (moveToDo.haveToBeDeleted()) {
				printAfterRefresh("Le domino � jouer ne peut pas �tre plac� et a �t� d�fauss�");
			} else {
				royaume.placerDomino(moveToDo);
				
				int Xref = moveToDo.getXref();
				int Yref = moveToDo.getYref();
				Direction dir = moveToDo.getDir();
				printAfterRefresh("A plac� son domino (" + Xref + "," + Yref + "," + dir +")");
			}
			joueur.getNextMoves().remove(moveToDo);
		} else {
			
			int Xref;
			int Yref;
			Direction dir;
			GUI.refreshBoard();
			boolean validChoice = false;
			GUI.choosePlaceForDomino(joueur, domino);
//			while(!validChoice) {
//				printAfterRefresh("Souhaitez vous placer ce domino ? (o/n)");
//				if (Outils.scanOuiNon()) {
//					
//					printAfterRefresh("Entrez la coordonn�e X de la case Ref");
//					Xref = Outils.scanInt();
//					printAfterRefresh("Entrez la coordonn�e Y de la case Ref");
//					Yref = Outils.scanInt();
//					if(!Royaume.isInGrid(Xref, Yref)) {
//						printAfterRefresh("Ces coordonn�es ne sont pas dans la grille");
//					}else {
//						refreshPlateau();
//						dir = Outils.scanDirection();
//						Move move = new Move(domino, Xref, Yref, dir);
//						if (royaume.placerDomino(move)) {
//							validChoice = true;
//							printAfterRefresh("Le domino a �t� plac� avec succ�s");
//						} else {
//							System.out.println("Vous ne pouvez pas placer ce domino ici, veuillez recommencer");
//						}
//						
//					}
//				} else {
//					System.out.println("Le domino a �t� d�fauss�");
//					validChoice = true;
//				}
//			}
		}
		dominosAjouer.remove(domino);
	}

	private void premierTour() {
		System.out.println(Outils.stringInFrame("==================== Debut de la partie ===================="));
		tirerNouveauxDominos();
		
		ArrayList<Roi> tempListeRois = new ArrayList<>();
		tempListeRois.addAll(listeRois);
		//L'ordre de jeu au premier tour est al�atoire
		for(int i =  0; i < listeRois.size(); i++) {
			int randomIndex = rand.nextInt(tempListeRois.size());
			Roi roi = tempListeRois.get(randomIndex);
			currentRoi = roi;
			GUI.refreshBoard();
			choisirDomino(roi);
			tempListeRois.remove(roi);
		}
	}

	private void ajouterJoueur(int i){
		GameColor couleur = couleursRestantes.get(0);
		System.out.println("Entre le nom du joueur "+ couleur);
		String nomJoueur = scan.nextLine();
		System.out.println("Voulez vous que ce joueur soit jou� par une IA ? (o/n)");
		boolean AI = Outils.scanOuiNon();
		listeJoueurs.add(new Joueur(nomJoueur, couleur, AI));
		// on enleve la couleur de la liste pour que chaque couleur soit individuelle.
		couleursRestantes.remove(0);	
	}
	
	private void ajouterIA() {
		GameColor couleur = couleursRestantes.get(0);
		listeJoueurs.add(new Joueur("Ia", couleur, true));
		couleursRestantes.remove(0);
	}

	/*
	 * Code de base copi� depuis https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	 */
	private void importDominos() {
		String csvFile = Partie.class.getProtectionDomain().getCodeSource().getLocation().getPath() +"/../dominos.csv";
        String line = "";
        String csvSplitBy = ",";
        Map<String, TypeTerrain> map = Outils.getDicoStringToType();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	
        	//saute la premi�re ligne qui d�crit le contenu de chaque colonne
        	br.readLine();

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] dominoString = line.split(csvSplitBy);
                
                int nbCouronne1 = Integer.valueOf(dominoString[0]);
                TypeTerrain type1 = map.get(dominoString[1]);
                int nbCouronne2 = Integer.valueOf(dominoString[2]);
                TypeTerrain type2 = map.get(dominoString[3]);
                int numeroDomino = Integer.valueOf(dominoString[4]);
                
                Case caseRef = new Case(type1, nbCouronne1);
                Case caseRot = new Case(type2, nbCouronne2);
                
                Domino domino = new Domino(caseRef, caseRot, numeroDomino);
                dominosPioche.add(domino);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private Domino getRandomFromPioche() {
		int randomIndex = rand.nextInt(dominosPioche.size());
		return dominosPioche.get(randomIndex);
	}
	
	private void removeDominosFromPioche(int nbrToRemove) {
		for(int i = 0; i < nbrToRemove; i++) {
			Domino dominoToRemove = getRandomFromPioche();
			dominosPioche.remove(dominoToRemove);
		}
	}
	
	public void refreshPlateau() {
		
		String[] stringRoyaumes = new String[listeJoueurs.size()]; 
		
		for (int i = 0; i < listeJoueurs.size(); i++) {
			Joueur joueur = listeJoueurs.get(i);
			String string = Outils.stringInFrame("Royaume " + joueur.getColorRoi(),22) + joueur.getRoyaume();
			stringRoyaumes[i] = string;
		}
		
		System.out.println(Outils.stringAside(5, stringRoyaumes));
		
		String dominosAChoisirWithTitle = Outils.stringInFrame("Dominos � choisir") +"\n" + listeDominosToString(dominosAChoisir);
		String dominosAJouerWithTitle = Outils.stringInFrame("Dominos � jouer") + "\n" + listeDominosToString(dominosAjouer);
		
		String listesDominos = Outils.stringAside(5, dominosAChoisirWithTitle, dominosAJouerWithTitle);
		System.out.println(listesDominos);
		
		System.out.println(Outils.stringInFrame("Tour du roi " + currentRoi.getColor()));
	}
	
	public void printAfterRefresh(String str) {
		refreshPlateau();
		System.out.println(str);
	}
	
	private Joueur getJoueurOfRoi(Roi roi) {
		Joueur joueur = null;
		for (Joueur joueurCourant : listeJoueurs) {
			if (joueurCourant.getListeRois().contains(roi)) {
				joueur =  joueurCourant;
				break;
			}
		}
		if (joueur == null) {
			System.out.println("pb");
		}
		
		return joueur;
	}

}
