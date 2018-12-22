import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Partie {
	static Scanner scan = new Scanner(System.in); 
	ArrayList<Color> couleursRestantes=new ArrayList<>();
	ArrayList<Joueur> listeJoueurs=new ArrayList<>();
	ArrayList<Domino> dominosAChoisir= new ArrayList<>();
	ArrayList<Domino> dominosAjouer = new ArrayList<Domino>();
	ArrayList<Domino> dominosPioche =new ArrayList<>();
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
		if(nbJoueur == 2) {
			for(int i = 0; i<nbJoueur ; i++) {
				listeJoueurs.get(i).addRoi();
				listeJoueurs.get(i).addRoi();
			}
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
		int randomIndex = rand.nextInt(listeRois.size());
	
		
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
	
	public void choisirDomino(Roi roi, int rangDomino) {
		roi.placerSurDomino(dominosAChoisir.get(rangDomino));
	}
	
	
	
	
	
	
	

}
