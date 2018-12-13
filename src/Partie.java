import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Partie {
	static Scanner scan = new Scanner(System.in); 
	ArrayList<Color> couleursRestantes=new ArrayList<>();
	ArrayList<Joueur> listeJoueurs=new ArrayList<>();
	ArrayList<Domino> dominoAJouer= new ArrayList<>();
	ArrayList<Domino> dominosPioche =new ArrayList<>();
	int nbJoueur;
	
	

	public Partie(int nbJoueur) {
		// on prend une liste avec les couleures disponibles a attribuer 
		couleursRestantes.addAll(Arrays.asList(Color.values()));
		this.nbJoueur=nbJoueur;
		for(int i=0;i<nbJoueur;i++ ) {
			ajouterJoueur(i);
			
		}
	
		
	}
	private void ajouterJoueur(int i){
		System.out.println("entre le nom du joueur"+ (i+1)+" la con de toi");
		String nomJoueur = scan.nextLine();
		listeJoueurs.add(new Joueur(nomJoueur, couleursRestantes.get(0)));
		// on enleve la couleur de la liste pour que chaque couleur soit individuelle.
		couleursRestantes.remove(0);
		
		
		
		
	}

}
