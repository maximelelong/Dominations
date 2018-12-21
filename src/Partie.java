import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;


public class Partie {
	static Scanner scan = new Scanner(System.in); 
	ArrayList<Color> couleursRestantes=new ArrayList<>();
	ArrayList<Joueur> listeJoueurs=new ArrayList<>();
	ArrayList<Domino> dominoAJouer= new ArrayList<>();
	ArrayList<Domino> dominosPioche =new ArrayList<>();
	int nbJoueur;
	
	Random rand = new Random();

	public Partie(int nbJoueur) {
		// on prend une liste avec les couleurs disponibles � attribuer 
		couleursRestantes.addAll(Arrays.asList(Color.values()));
		this.nbJoueur=nbJoueur;
		for(int i=0;i<nbJoueur;i++ ) {
			ajouterJoueur(i);
		}
		//Attribution des rois
		if(nbJoueur == 2) {
			listeJoueurs.get(0).addRoi();
			listeJoueurs.get(0).addRoi();
			listeJoueurs.get(1).addRoi();
			listeJoueurs.get(1).addRoi();
		}
	}
	
	private void premierTour() {
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
			dominoAJouer.add(domino);
		}
		
		//Permet d'ordonner les dominos selon leur nombre
		dominoAJouer.sort(Comparator.comparing(Domino::getNumero));
		
	}
	
	

}
