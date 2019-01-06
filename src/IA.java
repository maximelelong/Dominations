import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IA {
	
	public static Domino choisirBestDomino(Royaume royaume, ArrayList<Domino> dominosAChoisirRestant) {
		
		HashMap<Domino, Integer> dicoDominoBestScore = new HashMap<Domino, Integer>();
		
		//il faudrait ajouter virtuellement le domino qu'il a choisi au tour d'avant

		for (Domino domino : dominosAChoisirRestant) {
			
			if (Move.getPossibleMoves(royaume, domino).isEmpty()) {
				//si on ne peut pas jouer ce domino => on lui donne un score négatif
				dicoDominoBestScore.put(domino, -1);
			} else {
				Move bestMoveWithDomino = choisirBestMove(royaume, domino);
				int bestScoreWithDomino = royaume.getScoreAfterMove(domino, bestMoveWithDomino);
				dicoDominoBestScore.put(domino, bestScoreWithDomino);	
			}
		}
		
		int bestScore = Collections.max(dicoDominoBestScore.values());
		
		ArrayList<Domino> bestDominos = new ArrayList<Domino>();
		
		for (Domino domino : dicoDominoBestScore.keySet()) {
			
			if (dicoDominoBestScore.get(domino) == bestScore) {
				bestDominos.add(domino);
			}
		}
		
		//pour l'instant on prend juste le premier des dominos qui ont le meilleur score
		return bestDominos.get(0);
	}
	
	
	public static Move choisirBestMove(Royaume royaume,Domino domino) {
		
		Map<Move, Integer> dicoMoveScore = new HashMap<Move, Integer>();
		ArrayList<Move> possibleMoves = Move.getPossibleMoves(royaume, domino);
		
		for (Move move : possibleMoves) {
			
			int scoreMove = royaume.getScoreAfterMove(domino, move);
			dicoMoveScore.put(move, scoreMove);
		
			
		}
		int bestScore = 0;
	
		bestScore = Collections.max(dicoMoveScore.values());
		
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		
		for (Move move : dicoMoveScore.keySet()) {
			
			if (dicoMoveScore.get(move) == bestScore) {
				bestMoves.add(move);
			}
		}
		
		//pour l'instant on prend juste le premier des moves qui ont le meilleur score
		return bestMoves.get(0);
	}
}
