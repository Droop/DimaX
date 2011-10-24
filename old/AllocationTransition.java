package negotiation.negotiationframework.interaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import negotiation.negotiationframework.agent.AgentState;


import dima.basicagentcomponents.AgentIdentifier;

public class AllocationTransition<
Contract extends AbstractContractTransition<ActionSpec>, 
ActionSpec extends AbstractActionSpecification>
		extends HashSet<Contract> implements AbstractContractTransition<ActionSpec> {

	/**
	 *
	 */
	private static final long serialVersionUID = 652785940825256771L;
	private final AgentIdentifier analyser;
	private final long validityTime;
	private final Date creationTime = new Date();

	/*
	 *
	 */

	public AllocationTransition(final AgentIdentifier analyser,
			final long validityTime) {
		this.analyser = analyser;
		this.validityTime = validityTime;
	}

	public AllocationTransition(final AgentIdentifier analyser,
			final long validityTime, final Contract... cs) {
		this.analyser = analyser;
		this.validityTime = validityTime;
		for (final Contract c : cs)
			this.add(c);
	}

	public AllocationTransition(final AgentIdentifier analyser,
			final long validityTime, Collection<Contract> cs) {
		this.analyser = analyser;
		this.validityTime = validityTime;
		for (final Contract c : cs)
			this.add(c);
	}

	//
	// Methods
	//

	public AllocationTransition<Contract, ActionSpec> getNewTransition(
			final Contract c) {
		final AllocationTransition<Contract, ActionSpec> result = 
				new AllocationTransition<Contract, ActionSpec>(
				this.analyser, this.validityTime, this);
		result.add(c);
		return result;
	}

	/*
	 *
	 */

	@Override
	public AgentIdentifier getInitiator() {
		return this.analyser;
	}

	@Override
	public Collection<AgentIdentifier> getAllParticipants() {
		final Collection<AgentIdentifier> result = new ArrayList<AgentIdentifier>();
		for (final Contract c : this)
			result.addAll(c.getAllParticipants());
		return result;
	}

	@Override
	public Collection<AgentIdentifier> getAllInvolved() {
		final Collection<AgentIdentifier> result = new ArrayList<AgentIdentifier>(
				this.getAllParticipants());
		result.add(this.getInitiator());
		return result;
	}

	@Override
	public Collection<AgentIdentifier> getNotInitiatingParticipants() {
		final Collection<AgentIdentifier> result = new ArrayList<AgentIdentifier>(
				this.getAllParticipants());
		result.remove(this.getInitiator());
		return result;
	}

	public Collection<Contract> getAssociatedActions(AgentIdentifier id) {
		final Collection<Contract> result = new ArrayList<Contract>();
		for (final Contract c : this)
			if (c.getAllParticipants().contains(id))
				result.add(c);
		return result;
	}

	/*
	 *
	 */

	@Override
	public ContractIdentifier getIdentifier() {
		throw new RuntimeException("You should not use this!");
	}

	@Override
	public void setSpecification(final ActionSpec s) {
		throw new RuntimeException("You should not use this!");

	}


	@Override
	public <State extends AgentState> State computeResultingState(State s,
			AgentIdentifier id) {
		throw new RuntimeException("You should not use this!");
	}

	/*
	 *
	 */

	@Override
	public AllocationTransition<Contract, ActionSpec> clone() {
		throw new RuntimeException("You should not use this!");
		// AllocationTransition<Contract, ActionSpec> result = () super.clone();

	}

	@Override
	public long getUptime() {
		return new Date().getTime() - this.creationTime.getTime();
	}

	@Override
	public boolean hasReachedExpirationTime() {
		return this.getUptime() > this.validityTime;
	}

	@Override
	public boolean willReachExpirationTime(final long t) {
		return this.getUptime() + t > this.validityTime;
	}
}
// @Override
// public void setAccepted(AgentIdentifier id) {
// throw new RuntimeException("You should not use this!");
// }
//
// @Override
// public void setRejected(AgentIdentifier id) {
// throw new RuntimeException("You should not use this!");
// }

/*
 *
 */

// @Override
// public boolean isConsensual() {
// for (Contract c : this)
// if (!c.isConsensual())
// return false;
//
// return true;
// }
//
// @Override
// public boolean isAFailure() {
// for (Contract c : this)
// if (c.isAFailure())
// return true;
//
// return false;
// }
// @Override
// public long getUptime() {
// long uptime = Long.MIN_VALUE;
// for (final Contract c : this)
// uptime = Math.max(uptime, c.getUptime());
//
// return uptime;
// }
//
// @Override
// public boolean hasReachedExpirationTime() {
// throw new RuntimeException("You should not use this!");}
// // for (Contract c : this)
// // if (c.isAFailure())
// // return hasReachedExpirationTime();
// //
// // return false;
// //
// // }
//
// @Override
// public boolean willReachExpirationTime(final long t) {
// throw new RuntimeException("You should not use this!");}
// // for (Contract c : this)
// // if (c.isAFailure())
// // return willReachExpirationTime(t);
// //
// // return false;
// // }
