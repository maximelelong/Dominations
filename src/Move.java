import java.util.ArrayList;

public class Move {
	
	private int Xref;
	private int Yref;
	private Direction dir;
	
	public Move(int Xref, int Yref, Direction dir) {
		super();
		this.Xref = Xref;
		this.Yref = Yref;
		this.dir = dir;
	}

	public int getXref() {
		return Xref;
	}

	public void setXref(int xref) {
		Xref = xref;
	}

	public int getYref() {
		return Yref;
	}

	public void setYref(int yref) {
		Yref = yref;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}
	
	
	public int getXrot() {
		int Xrot;
		if(dir.equals(Direction.HAUT)) {
			Xrot = Xref;
		} else if (dir.equals(Direction.BAS)) {
			Xrot = Xref;
		} else if (dir.equals(Direction.DROITE)) {
			Xrot = Xref + 1;
		} else { //dir.equals(Direction.GAUCHE)
			Xrot = Xref - 1;
		}
		return Xrot;
	}
	
	public int getYrot() {
		int Yrot;
		if(dir.equals(Direction.HAUT)) {
			Yrot = Yref - 1;
		} else if (dir.equals(Direction.BAS)) {
			Yrot = Yref + 1;
		} else if (dir.equals(Direction.DROITE)) {
			Yrot = Yref;
		} else { //dir.equals(Direction.GAUCHE)
			Yrot = Yref;
		}
		return Yrot;
	}
	
	public static ArrayList<Move> getPossibleMoves(Royaume royaume, Domino domino) {
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		for (int x = 0; x < Royaume.largeurGrille; x++) {
			for (int y = 0; y < Royaume.hauteurGrille; y++) {
				for (Direction dir : Direction.values()) {
					Move move = new Move(x, y, dir);
					if(royaume.canPlace(domino, move)) {
						possibleMoves.add(move);
					}
				}
			}
		}
		return possibleMoves;
	}

}
