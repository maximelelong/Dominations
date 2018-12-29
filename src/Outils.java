import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Outils {
	public static Scanner scan= new Scanner(System.in);
	
	public static String scanString() {
		String string = scan.nextLine();
		return string;
	}
	
	public static int scanInt() {
		int entier = 0;
		boolean choixValide = false;
		while (!choixValide) {
			try {
				entier = Integer.valueOf(scan.nextLine());
				choixValide = true;
			} catch (Exception e) {
				System.out.println("Entrée invalide, veuillez entrer un entier ");
			}
		}
		return entier;
	}
	
	public static Direction scanDirection() {
		Direction direction = null;
		boolean choixValide = false;
		
		Map<Character, Direction> charToDirection = new HashMap<>();
		charToDirection.put('H', Direction.HAUT);
		charToDirection.put('B', Direction.BAS);
		charToDirection.put('G', Direction.GAUCHE);
		charToDirection.put('D', Direction.DROITE);
		
		System.out.println("Veuillez entrer une direction");
		System.out.println("'B' (BAS), 'H' (HAUT), 'G' (GAUCHE), 'D' (DROITE)");
		
		char charDir = ' ';
		while (!choixValide) {
			try {
				charDir = Character.valueOf(scanString().charAt(0));
				charDir = Character.toUpperCase(charDir);
				
				if(!charToDirection.containsKey(charDir)){
					System.out.println("Cette direction n'existe pas, veuillez recommencer");
				} else{
					direction = charToDirection.get(charDir);
					choixValide = true;
				}
			} catch (Exception e) {
				System.out.println("Saisie invalide, veuillez recommencer ");
			}
			
		}
		return direction;
	}
	
	public static boolean scanOuiNon() {
		boolean choixValide = false;
		char charChoix = ' ';
		while (!choixValide) {
			try {
				charChoix = Character.valueOf(scanString().charAt(0));
				charChoix = Character.toUpperCase(charChoix);
				if (charChoix == 'O' || charChoix == 'N') {
					choixValide = true;
				} else {
					System.out.println("Choix non disponible, veuillez recommencer");
				}
			} catch (Exception e) {
				System.out.println("Saisie invalide, veillez recommencer");
			}
		}
		if (charChoix == 'O') {
			return true;
		} else
			return false;
	}
	
	public static Map<String, TypeTerrain> getDicoStringToType() {
		Map<String, TypeTerrain> map = new HashMap<>();
		
		map.put("Champs", TypeTerrain.CHAMPS);
		map.put("Foret", TypeTerrain.FORET);
		map.put("Mer", TypeTerrain.MER);
		map.put("Prairie", TypeTerrain.PRAIRIE);
		map.put("Mine", TypeTerrain.MINE);
		map.put("Montagne", TypeTerrain.MONTAGNE);	
		
		return map;
	}
	
	
	public static String stringInFrame(String str) {
		String line = "====";
		for (int i = 0; i < str.length(); i++) {
			line += "=";
		}
		
		String result = line + "\n| " + str + " |\n" +line;
		return result;
	}
}
