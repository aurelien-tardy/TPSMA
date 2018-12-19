package agent.strategy;

import java.util.List;
import java.util.Random;

import agent.rlagent.RLAgent;
import environnement.Action;
import environnement.Action2D;
import environnement.Etat;
/**
 * Strategie qui renvoit un choix aleatoire avec proba epsilon, un choix glouton (suit la politique de l'agent) sinon
 * @author lmatignon
 *
 */
public class StrategyGreedy extends StrategyExploration{
	/**
	 * parametre pour probabilite d'exploration
	 */
	protected double epsilon;
	private Random rand=new Random();
	
	
	
	public StrategyGreedy(RLAgent agent,double epsilon) {
		super(agent);
		this.epsilon = epsilon;
	}

	@Override
	public Action getAction(Etat _e) {//renvoi null si _e absorbant
		double d =rand.nextDouble();
		List<Action> actions;
		if (this.agent.getActionsLegales(_e).isEmpty()){
			return null;
		}
	
		//VOTRE CODE ICI
		if(d<epsilon){
		// Exploration
			int i_rand = rand.nextInt(4);
			switch(i_rand){
			case 0:
				return Action2D.NORD;
			case 1:
				return Action2D.EST;
			case 2:
				return Action2D.SUD;
			case 3:
				return Action2D.OUEST;
			}
			
		}else{
		// Glouton	
			
			return this.agent.getPolitique(_e).get(0);
			
			
		}
		
		return null;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
		System.out.println("epsilon:"+epsilon);
	}

/*	@Override
	public void setAction(Action _a) {
		// TODO Auto-generated method stub
		
	}*/

}
