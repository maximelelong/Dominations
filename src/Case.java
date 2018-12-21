
public class Case {
	
	private TypeTerrain typeTerrain;
	private int nbCouronnes;
	@SuppressWarnings("null")
	private int X = (Integer) null;
	@SuppressWarnings("null")
	private int Y = (Integer) null;
	
	public Case() {
		typeTerrain = TypeTerrain.NONE;
		nbCouronnes = 0;
	}
	
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
	
	public void setTypeTerrain(TypeTerrain type) {
		typeTerrain = type;
	}

	public int getNbCouronnes() {
		return nbCouronnes;
	}
	
	public boolean isEmpty() {
		if(this.typeTerrain.equals(TypeTerrain.NONE))
			return true;
		else
			return false;
	}

}