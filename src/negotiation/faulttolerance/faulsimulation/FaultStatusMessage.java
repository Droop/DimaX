package negotiation.faulttolerance.faulsimulation;

import negotiation.negotiationframework.interaction.ResourceIdentifier;
import dima.basiccommunicationcomponents.Message;

public class FaultStatusMessage extends Message {
	private static final long serialVersionUID = -7015696793109757279L;

	final ResourceIdentifier r;

	public FaultStatusMessage(ResourceIdentifier r) {
		super();
		this.r = r;
	}

	public ResourceIdentifier getHost() {
		return this.r;
	}
}