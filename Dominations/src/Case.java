
public class Case {
	
	private TypeTerrain typeTerrain;
	private int nbCouronnes;
	private int X = (Integer) null;
	private int Y = (Integer) null;
	
	public Case(TypeTerrain type, int nombrebCouronnes) {
		typeTerrain = type;
		nbCouronnes = nombrebCouronnes;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public TypeTerrain getTypeTerrain() {
		return typeTerrain;
	}

	public int getNbCouronnes() {
		return nbCouronnes;
	}

}


