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
	int nbJoueur;
	
	Random rand = new Random();

	public Partie(int nbJoueur) {
		//import des dominos depuis le fichier csv
		importDominos();
		
		// on prend une liste avec les couleurs disponibles ï¿½ attribuer 
		couleursRestantes.addAll(Arrays.asList(Color.values()));
		this.nbJoueur=nbJoueur;
		for(int i=0;i<nbJoueur;i++ ) {
			ajouterJoueur(i);
		}
		
		//Attribution des rois
		
		//s'il y a 2 joueurs => 2 rois par joueur
		if(nbJoueur == 2) {
			for(int i = 0; i<nbJoueur ; i++) {
				listeJoueurs.get(i).addRoi();
				listeJoueurs.get(i).addRoi();
				
			}
		//sinon => 1 roi par joueur
		} else {
			for(int i = 0; i<nbJoueur ; i++) {
				listeJoueurs.get(i).addRoi();
			}
		}
		
		for (Joueur joueur : listeJoueurs) {
			for (Roi roi : joueur.getListeRois()) {
				listeRois.add(roi);
			}
		}
		
		premierTour();
		
		while (dominosPioche.size() != 0) {
			nouveauTour();
		}
	}
	
	public void nouveauTour() {
		dominosAjouer.addAll(dominosAChoisir);
		dominosAChoisir.clear();
		tirerNouveauxDominos();
		for (Domino dominoAjouer : dominosAjouer) {
			Roi roi = dominoAjouer.getRoi();
			System.out.println(Outils.stringInFrame("Tour du roi " + roi.getColor()));
			
			//le joueur choisi un domino pour le prochain tour
			choisirDomino(roi);
			dominoAjouer.removeRoi();			
			
			//le joueur choisit ce qu'il fait du domino choisi au tour précédent
			if (roi.getRoyaume().isFull()) {
				System.out.println("Le royaume du roi " + roi.getColor() + " est plein");
				System.out.println("Le domino à jouer est défaussé");
			} else {
				demandeOuPlacerDomino(dominoAjouer, roi);
			}
			roi.getRoyaume().printRoyaume();
		}
		dominosAjouer.clear();
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
	
	public void printDominosAchoisir() {
		for (int i = 0; i < dominosAChoisir.size(); i++) {
			Domino domino = dominosAChoisir.get(i);
			System.out.println("(" + i + ")");
			System.out.println(domino);
			if (domino.isChoosed()) {
				System.out.println("Choisi par le roi " + domino.getRoi().getColor());
			}
			System.out.println("");
		}
	}	
	
	private void choisirDomino(Roi roi) {
		System.out.println("Quel domino choisissez-vous ? \n");
		printDominosAchoisir();
		int rangDomino = -1;
		
		while(rangDomino < 0 || rangDomino >= dominosAChoisir.size()) {
			try {
				rangDomino = Integer.valueOf(scan.nextLine());
				if(dominosAChoisir.get(rangDomino).isChoosed()) {
					System.out.println("Ce domino a déjà été choisi, veuillez recommencer");
					rangDomino = -1;
				}
			} catch (Exception e) {
				System.out.println("Choix invalide, veuillez recommencer :");
			}
		}
		Domino dominoChoisi = dominosAChoisir.get(rangDomino);
		dominoChoisi.setRoi(roi);
	}

	private void demandeOuPlacerDomino(Domino domino, Roi roi) {
		int Xref;
		int Yref;
		Direction dir;
		boolean validChoice = false;
	
		roi.getRoyaume().printRoyaume();
	
		while(!validChoice) {
			System.out.println("Souhaitez vous placer ce domino ? (o/n)");
			if (Outils.scanOuiNon()) {
	
				System.out.println("Entrez la coordonnée X de la case Ref");
				Xref = Outils.scanInt();
				System.out.println("Entrez la coordonnée Y de la case Ref");
				Yref = Outils.scanInt();
				if(!Royaume.isInGrid(Xref, Yref)) {
					System.out.println("Ces coordonnées ne sont pas dans la grille");
				}else {
					dir = Outils.scanDirection();
					if (roi.getRoyaume().placerDomino(domino, Xref, Yref, dir)) {
						validChoice = true;
						System.out.println("Le domino a été placé avec succès");
					} else {
						System.out.println("Vous ne pouvez pas placer ce domino ici, veuillez recommencer");
					}
	
				}
			} else {
				System.out.println("Le domino a été défaussé");
			}
		}
	}

	private void premierTour() {
		tirerNouveauxDominos();
		ArrayList<Roi> tempListeRois = new ArrayList<>();
		tempListeRois.addAll(listeRois);
		//L'ordre de jeu au premier tour est aléatoire
		for(int i =  0; i < listeRois.size(); i++) {
			int randomIndex = rand.nextInt(tempListeRois.size());
			Roi roi = tempListeRois.get(randomIndex);
			System.out.println(Outils.stringInFrame("Tour du roi " + roi.getColor()));
			choisirDomino(roi);
			tempListeRois.remove(roi);
		}
	}

	private void ajouterJoueur(int i){
		Color couleur = couleursRestantes.get(0);
		System.out.println("Entre le nom du joueur "+ couleur);
		String nomJoueur = scan.nextLine();
		listeJoueurs.add(new Joueur(nomJoueur, couleur));
		// on enleve la couleur de la liste pour que chaque couleur soit individuelle.
		couleursRestantes.remove(0);	
	}

	/*
	 * Copié depuis https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
	 */
	private void importDominos() {
		String csvFile = "C:\\Users\\maxim\\eclipse-workspace\\Dominations\\dominos.csv";
        String line = "";
        String cvsSplitBy = ",";
        Map<String, TypeTerrain> map = Outils.getDicoStringToType();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	
        	//saute la première ligne qui décrit le contenu de chaque colonne
        	br.readLine();

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] dominoString = line.split(cvsSplitBy);
                
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

}
