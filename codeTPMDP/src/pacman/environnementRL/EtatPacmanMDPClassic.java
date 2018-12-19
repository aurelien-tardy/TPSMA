package pacman.environnementRL;

import java.util.ArrayList;
import java.util.Arrays;

import pacman.elements.StateAgentPacman;
import pacman.elements.StateGamePacman;
import environnement.Etat;
import environnement.Etat2D;
/**
 * Classe pour définir un etat du MDP pour l'environnement pacman avec QLearning tabulaire

 */
public class EtatPacmanMDPClassic implements Etat , Cloneable{

	private ArrayList<Integer> pos_ghosts;
	private ArrayList<Integer> pos_pacmans;
	
	public EtatPacmanMDPClassic(StateGamePacman _stategamepacman){
		
		pos_ghosts = new ArrayList<Integer>();
		pos_pacmans = new ArrayList<Integer>();
		
		int nb_ghosts = _stategamepacman.getNumberOfGhosts();
		for(int i = 0;i<nb_ghosts;i++){
			pos_ghosts.add(_stategamepacman.getGhostState(i).getX());
			pos_ghosts.add(_stategamepacman.getGhostState(i).getY());
		}
		int nb_pacmans = _stategamepacman.getNumberOfPacmans();
		for(int i = 0;i<nb_pacmans;i++){
			pos_pacmans.add(_stategamepacman.getPacmanState(i).getX());
			pos_pacmans.add(_stategamepacman.getPacmanState(i).getY());
		}
	}
	
	@Override
	public String toString() {
		
		return "";
	}
	
	
	public Object clone() {
		EtatPacmanMDPClassic clone = null;
		try {
			// On recupere l'instance a renvoyer par l'appel de la 
			// methode super.clone()
			clone = (EtatPacmanMDPClassic)super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implementons 
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		


		// on renvoie le clone
		return clone;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pos_ghosts == null) ? 0 : pos_ghosts.hashCode());
		result = prime * result + ((pos_pacmans == null) ? 0 : pos_pacmans.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else
			return false;
	}

	
	
	

}

