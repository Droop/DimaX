package negotiation.negotiationframework.strategy.evaluation;

import dima.introspectionbasedagents.services.library.information.NoInformationAvailableException;
import negotiation.negotiationframework.interaction.AbstractActionSpecification;
import negotiation.negotiationframework.interaction.AbstractContractTransition;

public interface AbstractStrategicEvaluationModule
<Contract extends AbstractContractTransition<ActionSpec>,
ActionSpec extends AbstractActionSpecification> {

	//Evaluation under uncertainty
	public int strategiclyCompare(Contract c1, Contract c2) throws NoInformationAvailableException;	
}
