package agent.planningagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import util.HashMapUtil;

import java.util.HashMap;

import environnement.Action;
import environnement.Etat;
import environnement.IllegalActionException;
import environnement.MDP;
import environnement.Action2D;

/**
 * Cet agent met a jour sa fonction de valeur avec value iteration et choisit
 * ses actions selon la politique calculee.
 * 
 * @author laetitiamatignon
 *
 */
public class ValueIterationAgent extends PlanningValueAgent {
	/**
	 * discount facteur
	 */
	protected double gamma;

	/**
	 * fonction de valeur des etats
	 */
	protected HashMap<Etat, Double> V;

	/**
	 *
	 * @param gamma
	 * @param mdp
	 */
	public ValueIterationAgent(double gamma, MDP mdp) {
		super(mdp);
		this.gamma = gamma;
		// *** VOTRE CODE
		// on initialise V0, toutes les valeurs à 0.0
		this.V = new HashMap<Etat, Double>();		
		for (Etat it_etat : this.mdp.getEtatsAccessibles()) {
			this.V.put(it_etat, 0.0);
		}
	}

	public ValueIterationAgent(MDP mdp) {
		this(0.9, mdp);

	}

	/**
	 *
	 * Mise a jour de V: effectue UNE iteration de value iteration (calcule
	 * V_k(s) en fonction de V_{k-1}(s')) et notifie ses observateurs. Ce n'est
	 * pas la version inplace (qui utilise nouvelle valeur de V pour mettre a
	 * jour ...)
	 */
	@Override
	public void updateV() {
		// delta est utilise pour detecter la convergence de l'algorithme
		// lorsque l'on planifie jusqu'a convergence, on arrete les iterations
		// lorsque
		// delta < epsilon
		this.delta = 0.0;
		// *** VOTRE CODE

		HashMap<Etat, Double> cloneV =(HashMap<Etat, Double>) this.V.clone();
		
		Double max_courant = 0.0, somme_courante, new_min = 100000000.0, new_max = 0.0;
		for (Etat it_etatD : cloneV.keySet()) {
			max_courant = 0.0;
			for (Action it_action : this.mdp.getActionsPossibles(it_etatD)) {
				somme_courante = getUpdate(it_etatD, it_action);
				if (somme_courante > max_courant) {
					max_courant = somme_courante;
				}
			}
			cloneV.put(it_etatD, max_courant);
			
			if(max_courant>new_max){
				new_max = max_courant;
			}else if(max_courant<new_min){
				new_min = max_courant;
			}
		}
		this.V = cloneV;
		// mise a jour vmax et vmin pour affichage du gradient de couleur:
		// vmax est la valeur max de V pour tout s
		// vmin est la valeur min de V pour tout s
		// ...
		this.vmax = new_max;
		this.vmin = new_min;
		// ******************* laisser notification a la fin de la methode
		this.notifyObs();
	}

	/**
	 * renvoi l'action executee par l'agent dans l'etat e Si aucune actions
	 * possibles, renvoi Action2D.NONE
	 */
	@Override
	public Action getAction(Etat e) {
		// *** VOTRE CODE

		List<Action> actions = this.getPolitique(e);
		if (actions.size() == 0) {
			return Action2D.NONE;
		} else {
			return actions.get(rand.nextInt(actions.size()));
		}

	}

	@Override
	public double getValeur(Etat _e) {
		// *** VOTRE CODE
		return this.V.get(_e);
	}

	/**
	 * renvoi action(s) de plus forte(s) valeur(s) dans etat (plusieurs actions
	 * sont renvoyees si valeurs identiques, liste vide si aucune action n'est
	 * possible)
	 */
	@Override
	public List<Action> getPolitique(Etat _e) {
		// *** VOTRE CODE

		// retourne action de meilleure valeur dans _e selon V,
		// retourne liste vide si aucune action legale (etat absorbant)
		List<Action> returnactions = new ArrayList<Action>();
		Double max = -1.0, somme_courante;

		for (Action action : mdp.getActionsPossibles(_e)) {
			somme_courante = getUpdate(_e, action);
				if (somme_courante >= max) {
					if (somme_courante.equals(max)) {
						returnactions.add(action);
					} else {
						max = somme_courante;
						returnactions.clear();
						returnactions.add(action);
					}
				}
		}
		return returnactions;

	}
	
	public double getUpdate(Etat _e, Action _a){
		double output=0.0, oldV, recompense;
		Map<Etat, Double> m = null;
		try {
			m = this.mdp.getEtatTransitionProba(_e, _a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Etat it_etat : m.keySet()) {
			oldV = this.V.get(it_etat);
			recompense = this.mdp.getRecompense(_e, _a, it_etat);
			output += m.get(it_etat) * (recompense + this.gamma * oldV);
		}		
		return output;
	}

	@Override
	public void reset() {
		super.reset();

		this.V.clear();
		for (Etat etat : this.mdp.getEtatsAccessibles()) {
			V.put(etat, 0.0);
		}
		this.notifyObs();
	}

	public HashMap<Etat, Double> getV() {
		return V;
	}

	public double getGamma() {
		return gamma;
	}

	@Override
	public void setGamma(double _g) {
		System.out.println("gamma= " + gamma);
		this.gamma = _g;
	}

}
