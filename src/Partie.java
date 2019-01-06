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
	ArrayList<Color> couleursRestantes=new ArrayList<>();
	ArrayList<Domino> dominosPioche =new ArrayList<>();
	ArrayList<Joueur> listeJoueurs=new ArrayList<>();
	ArrayList<Domino> dominosAChoisir= new ArrayList<>();
	ArrayList<Domino> dominosAjouer = new ArrayList<Domino>();
	ArrayList<Roi> listeRois = new ArrayList<Roi>();
	Roi currentRoi;
	int nbJoueur;
	
	Random rand = new Random();

	public Partie(int nbJoueur) {
		
		this.nbJoueur=nbJoueur;
		
		//import des dominos depuis le fichier csv
		importDominos();
		
		// on prend une liste avec les couleurs disponibles ï¿½ attribuer 
		couleursRestantes.addAll(Arrays.asList(Color.values()));
		for(int i=0;i<nbJoueur;i++ ) {
			ajouterJoueur(i);
		}
		
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
			//vérifie si au moins un des royaume n'est pas plein => il peut encore jouer => nouveau tour
			for (Roi roi : listeRois) {
				if (!roi.getRoyaume().isFull()) {
					royaumesAllFull = false;
					break;
				}
			}
			
			nouveauTour();
			
		}
		
		//Calcul du score de chaque joueur
		for (Joueur joueur : listeJoueurs) {
			int score = 0;
			for (Roi roi : joueur.getListeRois()) {
				score += roi.getRoyaume().calculerScore();
			}
			joueur.setScore(score);
		}
		
		//On ordonne les joueurs selon leur score (dans l'ordre décroissant)
		listeJoueurs.sort(Comparator.comparing(Joueur::getScore).reversed());
		
		
		System.out.println("");
		System.out.println(Outils.stringInFrame("Résultats",5));
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
			choisirDomino(roi);
			dominoAJouer.removeRoi();			
			
			//le joueur choisit ce qu'il fait du domino choisi au tour précédent
			if (roi.getRoyaume().isFull()) {
				System.out.println("Le royaume du roi " + roi.getColor() + " est plein");
				System.out.println("Le domino à jouer est défaussé");
			} else {
				demandeOuPlacerDomino(dominoAJouer, roi);
			}
			i -= 1;
			dominosAjouer.remove(dominoAJouer);
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
		 * Dans cette méthode, tous les indices affichés ont un "+1"
		 * pour compter à partir de 1 pour l'utilisateur et pas à partir de 0 		
		 */
		
		int rangDomino;
		
		if (roi.isAI()) {
			
			
			ArrayList<Domino> dominosDispo = new ArrayList<>();
			for (Domino domino : dominosAChoisir) {
				if (!domino.isChoosed()) {
					dominosDispo.add(domino);
				}
			}
			
			Domino dominoChoisi = IA.choisirBestDomino(roi.getRoyaume(), dominosDispo);
			rangDomino = dominosAChoisir.indexOf(dominoChoisi);
			
			printAfterRefresh("A choisi le " + (rangDomino+1) + "e domino");
			
		} else {			
			printAfterRefresh("Quel domino choisissez-vous pour le prochain tour ? \n");
			rangDomino = -1;
			
			while(rangDomino < 0 || rangDomino >= dominosAChoisir.size()) {
				try {
					rangDomino = Integer.valueOf(scan.nextLine()) - 1;
					if(dominosAChoisir.get(rangDomino).isChoosed()) {
						printAfterRefresh("Ce domino a déjà été choisi, veuillez recommencer");
						rangDomino = -1;
					}
				} catch (Exception e) {
					printAfterRefresh("Choix invalide, veuillez recommencer :");
				}
			}
		}
		
		Domino dominoChoisi = dominosAChoisir.get(rangDomino);
		dominoChoisi.setRoi(roi);
	}

	private void demandeOuPlacerDomino(Domino domino, Roi roi) {
		Royaume royaume = roi.getRoyaume();
		
		if (roi.isAI()) {
			ArrayList<Move> possibleMoves = Move.getPossibleMoves(royaume, domino); 
			if (possibleMoves.size() == 0) {
				printAfterRefresh("Le domino à jouer ne peut pas être placé et a été défaussé");
			} else {
				Move bestMove = IA.choisirBestMove(royaume, domino);
				royaume.placerDomino(domino, bestMove);
				int Xref = bestMove.getXref();
				int Yref = bestMove.getYref();
				Direction dir = bestMove.getDir();
				printAfterRefresh("A placé son domino (" + Xref + "," + Yref + "," + dir +")");
			}
		} else {
			
			int Xref;
			int Yref;
			Direction dir;
			
			boolean validChoice = false;
			while(!validChoice) {
				printAfterRefresh("Souhaitez vous placer ce domino ? (o/n)");
				if (Outils.scanOuiNon()) {
					
					printAfterRefresh("Entrez la coordonnée X de la case Ref");
					Xref = Outils.scanInt();
					printAfterRefresh("Entrez la coordonnée Y de la case Ref");
					Yref = Outils.scanInt();
					if(!Royaume.isInGrid(Xref, Yref)) {
						printAfterRefresh("Ces coordonnées ne sont pas dans la grille");
					}else {
						refreshPlateau();
						dir = Outils.scanDirection();
						Move move = new Move(Xref, Yref, dir);
						if (royaume.placerDomino(domino, move)) {
							validChoice = true;
							printAfterRefresh("Le domino a été placé avec succès");
						} else {
							System.out.println("Vous ne pouvez pas placer ce domino ici, veuillez recommencer");
						}
						
					}
				} else {
					System.out.println("Le domino a été défaussé");
					validChoice = true;
				}
			}
			
		}
	}

	private void premierTour() {
		System.out.println(Outils.stringInFrame("==================== Debut de la partie ===================="));
		tirerNouveauxDominos();
		
		ArrayList<Roi> tempListeRois = new ArrayList<>();
		tempListeRois.addAll(listeRois);
		//L'ordre de jeu au premier tour est aléatoire
		for(int i =  0; i < listeRois.size(); i++) {
			int randomIndex = rand.nextInt(tempListeRois.size());
			Roi roi = tempListeRois.get(randomIndex);
			currentRoi = roi;
			choisirDomino(roi);
			tempListeRois.remove(roi);
		}
	}

	private void ajouterJoueur(int i){
		Color couleur = couleursRestantes.get(0);
		System.out.println("Entre le nom du joueur "+ couleur);
		String nomJoueur = scan.nextLine();
		System.out.println("Voulez vous que ce joueur soit joué par une IA ? (o/n)");
		boolean AI = Outils.scanOuiNon();
		listeJoueurs.add(new Joueur(nomJoueur, couleur, AI));
		// on enleve la couleur de la liste pour que chaque couleur soit individuelle.
		couleursRestantes.remove(0);	
	}

	/*
	 * Code de base copié depuis https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	 */
	private void importDominos() {
		String csvFile = "C:\\Users\\maxim\\git\\Dominations\\dominos.csv";
        String line = "";
        String csvSplitBy = ",";
        Map<String, TypeTerrain> map = Outils.getDicoStringToType();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	
        	//saute la première ligne qui décrit le contenu de chaque colonne
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
		int nbreRois = listeRois.size();
		
		String[] stringRoyaumes = new String[nbreRois]; 
		
		for (int i = 0; i < nbreRois; i++) {
			Roi roi = listeRois.get(i);
			String string = Outils.stringInFrame("Royaume " + roi.getColor(),22) + roi.getRoyaume();
			stringRoyaumes[i] = string;
		}
		
		System.out.println(Outils.stringAside(5, stringRoyaumes));
		
		String dominosAChoisirWithTitle = Outils.stringInFrame("Dominos à choisir") +"\n" + listeDominosToString(dominosAChoisir);
		String dominosAJouerWithTitle = Outils.stringInFrame("Dominos à jouer") + "\n" + listeDominosToString(dominosAjouer);
		
		String listesDominos = Outils.stringAside(5, dominosAChoisirWithTitle, dominosAJouerWithTitle);
		System.out.println(listesDominos);
		
		System.out.println(Outils.stringInFrame("Tour du roi " + currentRoi.getColor()));
	}
	
	public void printAfterRefresh(String str) {
		refreshPlateau();
		System.out.println(str);
	}

}
