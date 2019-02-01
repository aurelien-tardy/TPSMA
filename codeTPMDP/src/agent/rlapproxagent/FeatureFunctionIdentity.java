package agent.rlapproxagent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import environnement.Action;
import environnement.Action2D;
import environnement.Etat;
import javafx.util.Pair;
/**
 * Vecteur de fonctions caracteristiques phi_i(s,a): autant de fonctions caracteristiques que de paire (s,a),
 * <li> pour chaque paire (s,a), un seul phi_i qui vaut 1  (vecteur avec un seul 1 et des 0 sinon).
 * <li> pas de biais ici 
 * 
 * @author laetitiamatignon
 *
 */
public class FeatureFunctionIdentity implements FeatureFunction {
	//*** VOTRE CODE
	protected int size;
	protected HashMap<Pair<Etat, Action>, Double> features;
	protected int index;
	
	public FeatureFunctionIdentity(int _nbEtat, int _nbAction){
		//*** VOTRE CODE
		this.size = _nbEtat*_nbAction;
		this.index = 0;
		this.features = new HashMap<>();
	}
	
	@Override
	public int getFeatureNb() {
		//*** VOTRE CODE
		return size;
	}

	@Override
	public double[] getFeatures(Etat e,Action a){
		//*** VOTRE CODE
		double[] output = new double[size];
		features.put(new Pair<>(e,a), 1.0);
		for(int i = 0 ; i<size ; i++){
			if(i == index){
				output[i] = 1.0;
				break;
			}
			output[i] = 0.0;
		}
		index++;
		return output;
	}
	

}
