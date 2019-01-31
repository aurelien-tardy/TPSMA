package agent.strategy;

import java.util.List;
import java.util.Random;

import agent.rlagent.RLAgent;
import environnement.Action;
import environnement.Action2D;
import environnement.Etat;

/**
 * Strategie qui renvoit un choix aleatoire avec proba epsilon, un choix glouton
 * (suit la politique de l'agent) sinon
 * 
 * @author lmatignon
 *
 */
public class StrategyGreedy extends StrategyExploration {
	/**
	 * parametre pour probabilite d'exploration
	 */
	protected double epsilon;
	private Random rand = new Random();

	public StrategyGreedy(RLAgent agent, double epsilon) {
		super(agent);
		this.epsilon = epsilon;
	}

	@Override
	public Action getAction(Etat _e) {// renvori null si _e absorbant
		double d = rand.nextDouble();
		List<Action> actions;
		if (this.agent.getActionsLegales(_e).isEmpty()) {
			return null;
		}

		// VOTRE CODE ICI
		List<Action> actionsConsiderees;

		if (d <= this.getEpsilon()) {
			// Exploration
			actionsConsiderees = this.agent.getActionsLegales(_e);
		} else {
			// Choix greedy
			actionsConsiderees = this.agent.getPolitique(_e);
		}
		if (actionsConsiderees.size() == 0) {
			actionsConsiderees = this.agent.getActionsLegales(_e);
		}
		// On choisit une action au hasard parmi celles considérées
		return actionsConsiderees.get(rand.nextInt(actionsConsiderees.size()));
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
		System.out.println("epsilon:" + epsilon);
	}

	/*
	 * @Override public void setAction(Action _a) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 */

}
