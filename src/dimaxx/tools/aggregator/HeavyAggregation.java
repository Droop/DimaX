package dimaxx.tools.aggregator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Collection of elements mapped to their weight Delegate all aggregation
 * functions.
 * 
 * @author Sylvain Ductor
 * 
 * @param <Element>
 */
public abstract class HeavyAggregation<Element extends Object> extends
		TreeMap<Element, Double> implements
		AbstractCompensativeAggregation<Element>,
		FunctionnalCompensativeAggregator<Element>, AbstractDispersionAggregation,
		AbstractMinMaxAggregation<Element>, AbstractQuantileAggregation<Element>,
		UtilitaristAnalyser<Element> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4326518157900844655L;

	public HeavyAggregation() {
		super();
	}

	public HeavyAggregation(Comparator<? super Element> arg0) {
		super(arg0);
	}

	public HeavyAggregation(Map<? extends Element, ? extends Double> arg0) {
		super(arg0);
	}

	public HeavyAggregation(SortedMap<Element, ? extends Double> arg0) {
		super(arg0);
	}

	/*
	 * 
	 */

	public Double add(Element e) {
		return this.put(e, 1.);
	}

	/*
	 * 
	 */

	@Override
	public Element getMaxElement() {
		return this.lastKey();
	}

	@Override
	public Element getMinElement() {
		return this.firstKey();
	}

	@Override
	public double getVariance() {
		return FunctionalDispersionAgregator.getVariance(this, this.keySet());
	}

	@Override
	public double getEcartType() {
		return FunctionalDispersionAgregator.getEcartType(this, this.values());
	}

	@Override
	public double getVariationCoefficient() {
		return FunctionalDispersionAgregator.getVariationCoefficient(this, this.values());
	}

	/*
	 * 
	 */

	@Override
	public int getNumberOfAggregatedElements() {
		return this.size();
	}

	/*
	 * 
	 */

	@Override
	public Element getQuantile(int k, int q) {
		return FunctionalQuantileNMinMaxAggregator.getQuantile(
				new ArrayList<Element>(this.keySet()), k, q);
	}

	@Override
	public Element getFirstTercile() {
		return FunctionalQuantileNMinMaxAggregator
				.getFirstTercile(new ArrayList<Element>(this.keySet()));
	}

	@Override
	public Element getSecondTercile() {
		return FunctionalQuantileNMinMaxAggregator
				.getSecondTercile(new ArrayList<Element>(this.keySet()));
	}

	@Override
	public Element getMediane() {
		return FunctionalQuantileNMinMaxAggregator
				.getMediane(new ArrayList<Element>(this.keySet()));
	}

}
