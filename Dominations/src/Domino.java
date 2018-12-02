import java.awt.List;
import java.util.ArrayList;

public class Domino {
	private Case caseRef;
	private Case caseRot;
	
	public Domino(Case caseRef, Case caseRot) {
		this.caseRef = caseRef;
		this.caseRot = caseRot;
	}

	public Case getCaseRef() {
		return caseRef;
	}

	public Case getCaseRot() {
		return caseRot;
	}

}
