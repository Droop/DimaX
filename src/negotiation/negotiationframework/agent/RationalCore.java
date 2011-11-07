package negotiation.negotiationframework.agent;

import java.util.Collection;

import negotiation.negotiationframework.interaction.AbstractActionSpecification;
import negotiation.negotiationframework.interaction.AbstractContractTransition;
import dima.introspectionbasedagents.services.AgentCompetence;

public interface RationalCore<
ActionSpec extends AbstractActionSpecification,
PersonalState extends ActionSpec, 
Contract extends AbstractContractTransition<ActionSpec>>
		extends
		AgentCompetence<SimpleRationalAgent<ActionSpec, PersonalState, Contract>> {

	/*
	 *
	 */

//	public PersonalState getMyResultingState(PersonalState s, Contract c);
//
	public ActionSpec getMySpecif(PersonalState s, Contract c);

	public void execute(Contract c);

	/*
	 *
	 */

	public int getAllocationPreference(PersonalState s,
			Collection<Contract> c1, Collection<Contract> c2);

	public  Double evaluatePreference(PersonalState s1);
	

	/*
	 *
	 */

	public boolean IWantToNegotiate(PersonalState s);
}

// public boolean Iaccept(final PersonalState s1, final PersonalState s2);

// public boolean willAccept(AgentIdentifier id, final InformedState s1, final
// InformedState s2);

/*
 *
 */
// public int getMyPreference(PersonalState s1, PersonalState s2);

// public int getPreference(AgentIdentifier id, InformedState s1, InformedState
// s2);

/**
 * 
 * @param a
 *            : a given state
 * @return true if the agent constraints are respected on a
 */
// public Boolean respectRights(InformedState s);

/*
 *
 */

// public InformedState getResultingState(InformedState s, Contract c);
