import java.awt.Color;

public enum GameColor {
	ROUGE (Color.RED),
	VERT (Color.GREEN),
	BLEU (Color.BLUE),
	ROSE (Color.PINK);

	private Color awtColor = null;
	
	GameColor(Color awtColor) {
		this.awtColor= awtColor;	
	}
	public Color getAwtColor() {
		return awtColor;
	}
}
