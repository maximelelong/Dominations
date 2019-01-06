import java.util.ArrayList;
import java.util.Arrays;
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
	
	public static String stringInFrame(String str, int spaceBefore) {
		String line = "====";
		String spaces = "";
		for(int i = 0; i < spaceBefore; i++) {
			spaces += " ";
		}
	
		for (int i = 0; i < str.length(); i++) {
			line += "=";
		}
		
		String result = spaces + line + "\n" + spaces +"| " + str + " |\n" + spaces + line;
		return result;
	}
	public static String stringAside(int nbrSpacesBetween, String...strings) {
		
		if (strings.length == 1) {
			return strings[0];
		}
		
		String bigString = "";
		
		int maxNbrLines = 0;
		for (String string : strings) {
			String[] linesArray = string.split("\n");
			if (linesArray.length > maxNbrLines) {
				maxNbrLines = linesArray.length;
			}
		}
		
		ArrayList<ArrayList<String>> stringsArrayList = new ArrayList<ArrayList<String>>();
		
		
		for (String string : strings) {
			String[] linesArray = string.split("\n");
			ArrayList<String> lines = new ArrayList<String>(Arrays.asList(linesArray));
			
			int linesToAdd = maxNbrLines - lines.size();
			for(int i = 0; i < linesToAdd; i++) {
				lines.add("");
			}
			
			int lineMaxLenght = 0;
			for (String line : lines) {
				int lineLenght = line.length();
				if (lineLenght > lineMaxLenght) {
					lineMaxLenght = line.length();
				}
			}
			
			//On ajoute des espaces pour que chaque ligne soit de même longueur
			for(int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				int lineLenght = line.length();
				int spacesToAdd = lineMaxLenght - lineLenght;
				if (spacesToAdd > 0) {
					for(int j = 0; j < spacesToAdd; j++) {
						line += " ";
						lines.remove(i);
						lines.add(i, line);
					}
				}
			}
			
//			for (String line : lines) {
//				
//			}
			stringsArrayList.add(lines);
		}
		
		for (int i = 0; i < maxNbrLines; i++) {
			for (ArrayList<String> lines : stringsArrayList) {
				bigString += lines.get(i);
				for(int j = 0; j < nbrSpacesBetween; j++) {
					bigString += " ";
				}
			}
			bigString += "\n";
		}
		
		return bigString;
	}
}
