public class Domino {
	private Case caseRef;
	private Case caseRot;
	private int numero;
	
	public Domino(Case caseRef, Case caseRot, int numero) {
		this.caseRef = caseRef;
		this.caseRot = caseRot;
		this.numero = numero;
	}

	public Case getCaseRef() {
		return caseRef;
	}

	public Case getCaseRot() {
		return caseRot;
	}
	
	public int getNumero() {
		return numero;
	}

}