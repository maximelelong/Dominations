import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;


public class Partie extends Application {
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
	
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
	private void ajouterJoueur(int i){
		System.out.println("Entre le nom du joueur "+ (i+1));
		String nomJoueur = scan.nextLine();
		listeJoueurs.add(new Joueur(nomJoueur, couleursRestantes.get(0)));
		// on enleve la couleur de la liste pour que chaque couleur soit individuelle.
		couleursRestantes.remove(0);
		
		
		
		
	}

}
