
public enum TypeTerrain {
	NONE ("  "),
	FORET ("FOR"),
	CHAMPS ("CHA"),
	MER ("MER"),
	PRAIRIE ("PRA"),
	MINE ("MIN"),
	MONTAGNE ("MON"),
	CHATEAU ("ROI");
	
	private String str = "  ";
	
	TypeTerrain(String str) {
		this.str = str;
	}
	
	public String toString() {
		return str;
	}
}
