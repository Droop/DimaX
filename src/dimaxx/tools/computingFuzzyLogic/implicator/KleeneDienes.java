package dimaxx.tools.computingFuzzyLogic.implicator;

import dimaxx.tools.computingFuzzyLogic.controller.FuzzySubSet;

/**
 *
 * @author Sylvain Ductor
 */
public class KleeneDienes implements Implicateur {

	/**
	 *
	 */
	private static final long serialVersionUID = -768409035950068949L;

	@Override
	public FuzzySubSet r(final FuzzySubSet premisse,
			final FuzzySubSet conclusion) {
		return conclusion.sousEnsembleUnion(premisse.soustraction(1));
	}

}
