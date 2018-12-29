
public enum Color {
	ROUGE ("255;0;0"),
	VERT ("0;255;0"),
	BLEU ("0;0;255"),
	ROSE ("255;192;203");

	private String RGB ="";
	
	Color(String RGB) {
		this.RGB=RGB;	
	}
	public String getRGB() {
		return RGB;
	}
}
