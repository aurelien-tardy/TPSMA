package agent.rlagent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.util.Pair;
import environnement.Action;
import environnement.Environnement;
import environnement.Etat;

/**
 * Renvoi 0 pour valeurs initiales de Q
 * 
 * @author laetitiamatignon
 *
 */
public class QLearningAgent extends RLAgent {
	/**
	 * format de memorisation des Q valeurs: utiliser partout setQValeur car
	 * cette methode notifie la vue
	 */
	protected HashMap<Etat, HashMap<Action, Double>> qvaleurs;

	// AU CHOIX: vous pouvez utiliser une Map avec des Pair pour clés si vous
	// préférez
	// protected HashMap<Pair<Etat,Action>,Double> qvaleurs;

	/**
	 *
	 * @param alpha
	 * @param gamma
	 * @param Environnement
	 * 
	 */
	public QLearningAgent(double alpha, double gamma, Environnement _env) {
		super(alpha, gamma, _env);
		qvaleurs = new HashMap<Etat, HashMap<Action, Double>>();

	}

	/**
	 * renvoi action(s) de plus forte(s) valeur(s) dans l'etat e (plusieurs
	 * actions sont renvoyees si valeurs identiques) renvoi liste vide si
	 * aucunes actions possibles dans l'etat (par ex. etat absorbant)
	 * 
	 */
	@Override
	public List<Action> getPolitique(Etat e) {
		// retourne action de meilleures valeurs dans _e selon Q : utiliser
		// getQValeur()
		// retourne liste vide si aucune action legale (etat terminal)
		List<Action> returnactions = new ArrayList<Action>();
		if (this.getActionsLegales(e).size() == 0) {// etat absorbant;
													// impossible de le verifier
													// via environnement
			System.out.println("aucune action legale");
			return new ArrayList<Action>();
		}
		double max = 0.0;
		Action it_max = null;
		for (Action it_action : this.env.getActionsPossibles(e)) {
			if (this.getQValeur(e, it_action) >= max) {
				if (this.getQValeur(e, it_action)==max) {
					returnactions.add(it_action);
				} else {
					max = this.getQValeur(e, it_action);
					it_max = it_action;
				}
			}
		}
		returnactions.add(it_max);
		// *** VOTRE CODE
		return returnactions;

	}

	@Override
	public double getValeur(Etat e) {
		// *** VOTRE CODE
		double output = 0.0;
		double min = 100000000.0;
		if (!this.qvaleurs.containsKey(e)) {
			return output;
		}
		for (Action it_action : this.qvaleurs.get(e).keySet()) {
			if (this.getQValeur(e, it_action) > output) {
				output = this.getQValeur(e, it_action);
			}
		}
		
		// M�j de vmin et vmax
		if(output>this.vmax)
			this.vmax = output;
		if(output<this.vmin)
			this.vmin = output;
		
		return output;

	}

	@Override
	public double getQValeur(Etat e, Action a) {
		// *** VOTRE CODE
		double output = 0.0;
		if (e != null && a != null && this.qvaleurs.get(e) != null && this.qvaleurs.get(e).get(a) != null)
			output = this.qvaleurs.get(e).get(a);
		return output;
	}

	@Override
	public void setQValeur(Etat e, Action a, double d) {
		// *** VOTRE CODE

		if (!this.qvaleurs.containsKey(e)) {
			this.qvaleurs.put(e, new HashMap<Action, Double>());
		}

		this.qvaleurs.get(e).put(a, d);

		// mise a jour vmax et vmin pour affichage du gradient de couleur:
		// vmax est la valeur max de V pour tout s
		// vmin est la valeur min de V pour tout s
		// ...

		this.notifyObs();

	}

	/**
	 * mise a jour du couple etat-valeur (e,a) apres chaque interaction <etat
	 * e,action a, etatsuivant esuivant, recompense reward> la mise a jour
	 * s'effectue lorsque l'agent est notifie par l'environnement apres avoir
	 * realise une action.
	 * 
	 * @param e
	 * @param a
	 * @param esuivant
	 * @param reward
	 */
	@Override
	public void endStep(Etat e, Action a, Etat esuivant, double reward) {
		if (RLAgent.DISPRL)
			System.out.println("QL mise a jour etat " + e + " action " + a + " etat' " + esuivant + " r " + reward);

		// *** VOTRE CODE

		double maxActionSuivante = 0.0;

		for (Action it_action : this.env.getActionsPossibles(esuivant)) {
			if (getQValeur(esuivant, it_action) > maxActionSuivante) {
				maxActionSuivante = getQValeur(esuivant, it_action);
			}
		}

		double calcul = (1 - this.alpha) * getQValeur(e, a) + this.alpha * (reward + this.gamma * maxActionSuivante);

		setQValeur(e, a, calcul);

	}

	@Override
	public Action getAction(Etat e) {
		this.actionChoisie = this.stratExplorationCourante.getAction(e);
		return this.actionChoisie;
	}

	@Override
	public void reset() {
		super.reset();
		// *** VOTRE CODE
		for (Etat it_etat : this.qvaleurs.keySet()) {
			this.qvaleurs.get(it_etat).clear();
			for (Action it_action : this.qvaleurs.get(it_etat).keySet()) {
				this.qvaleurs.get(it_etat).put(it_action, 0.0);
			}
		}

		this.episodeNb = 0;
		this.notifyObs();
	}

}
