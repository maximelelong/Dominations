import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Partie {
	static Scanner scan = new Scanner(System.in); 
	ArrayList<Color> couleursRestantes=new ArrayList<>();
	ArrayList<Domino> dominosPioche =new ArrayList<>();
	ArrayList<Joueur> listeJoueurs=new ArrayList<>();
	ArrayList<Domino> dominosAChoisir= new ArrayList<>();
	ArrayList<Domino> dominosAjouer = new ArrayList<Domino>();
	int nbJoueur;
	
	Random rand = new Random();

	public Partie(int nbJoueur) {
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
	}
	
	private void premierTour() {
		tirerNouveauxDominos();
		List<Roi> listeRois = new ArrayList<Roi>();
		for (Joueur joueur : listeJoueurs) {
			for (Roi roi : joueur.getListeRois()) {
				listeRois.add(roi);
			}
		}
		//L'ordre de jeu au premier tour est aléatoire
		for(int i =  0; i < nbJoueur; i++) {
			int randomIndex = rand.nextInt(listeRois.size());
			Roi roi = listeRois.get(randomIndex);
			choisirDomino(roi);
			listeRois.remove(roi);
		}
	}
	
	
	
	private void ajouterJoueur(int i){
		System.out.println("Entre le nom du joueur "+ (i+1));
		String nomJoueur = scan.nextLine();
		listeJoueurs.add(new Joueur(nomJoueur, couleursRestantes.get(0)));
		// on enleve la couleur de la liste pour que chaque couleur soit individuelle.
		couleursRestantes.remove(0);	
	}
	
	public void tirerNouveauxDominos() {
		for (int i = 0; i < nbJoueur; i++) {
			int randomIndex = rand.nextInt(dominosPioche.size());
			Domino domino = dominosPioche.get(randomIndex);
			dominosPioche.remove(domino);
			dominosAChoisir.add(domino);
		}
		
		//Permet d'ordonner les dominos selon leur nombre
		dominosAChoisir.sort(Comparator.comparing(Domino::getNumero));
	}
	
	public void choisirDomino(Roi roi) {
		System.out.println("Quel domino choisissez-vous ? ");
		int rangDomino = -1;
		while(rangDomino < 0 || rangDomino > nbJoueur) {
			try {
				rangDomino = Integer.valueOf(scan.nextLine());
			} catch (Exception e) {
				System.out.println("Choix invalide, veuillez recommencer :");
			}
		}
		roi.placerSurDomino(dominosAChoisir.get(rangDomino));
	}
	
	
	
	
	
	
	

}
