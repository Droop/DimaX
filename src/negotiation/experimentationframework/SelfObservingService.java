package negotiation.experimentationframework;

import negotiation.faulttolerance.experimentation.ReplicationExperimentationProtocol;
import negotiation.negotiationframework.SimpleNegotiatingAgent;
import dima.introspectionbasedagents.annotations.MessageHandler;
import dima.introspectionbasedagents.annotations.StepComposant;
import dima.introspectionbasedagents.annotations.Transient;
import dima.introspectionbasedagents.services.BasicAgentCompetence;

public abstract class SelfObservingService
extends BasicAgentCompetence<SimpleNegotiatingAgent<?, ?,?>>{
	private static final long serialVersionUID = 496384107474313690L;

	
	public SelfObservingService() {
		super();
	}


	//
	//
	//

	protected abstract ExperimentationResults generateMyResults();

	@StepComposant(ticker=ReplicationExperimentationProtocol._state_snapshot_frequency)
	public void notifyMyState(){
		this.notify(this.generateMyResults());
	}

	private boolean hasSendedEnd=false;
	@StepComposant(ticker=ReplicationExperimentationProtocol._simulationTime)
	@Transient
	public boolean endSimulation(){
		//		this.logMonologue("this is the end my friend");
		if (!this.hasSendedEnd){
			getMyAgent().setAlive(false);
			this.notifyMyState();
			getMyAgent().observer.autoSendOfNotifications();
			this.hasSendedEnd=true;
		}
		return true;

	}

	@MessageHandler
	public void simulationEndORder(final SimulationEndedMessage s){
		if (getMyAgent().isAlive()){
			getMyAgent().setAlive(false);
			this.endSimulation();
		}
	}
}




//
//	@StepComposant(ticker=StaticParameters._simulationTime)
//	@Transient
//	public boolean endSimulation(){
//		this.logMonologue("this is the end my friend");
//		final Double lastActionTime = new Double(new Date().getTime() - this.lastAction.getTime());
//		this.notify(new AgentEndProtocolObs(
//				this.getIdentifier(),
//				this.protoTime.getRepresentativeElement(),
//				Math.max(this.getMyCurrentState().getMyMemCharge(), this.getMyCurrentState().getMyProcCharge()),
//				this.getMyCurrentState().getMyStateStatus(),
//				lastActionTime,
//				this.getMyCurrentState().getMyReplicas().size(),
//				this.getMyCurrentState().getMyReliability(),
//				this.getMyCurrentState().getMyDisponibility(),
//				this.getMyCurrentState().getMyCriticity(),
//				this.iAMDead));
//		this.notify(new AgentInfo());
//		this.observer.autoSendOfNotifications();
//		this.setAlive(false);
//		return true;
//	}
//
//	@StepComposant(ticker=StaticParameters._simulationTime)
//	@Transient
//	public boolean endSimulation(){
//		//		if (new Date().getTime() - StepTickersParameters.creation.getTime()>NegotiationSimulationParameters._simulationTime){
//		this.notify(new HostInfo());
//		this.notify(new HostEnd());
//		this.observer.autoSendOfNotifications();
//		this.setAlive(false);
//		return true;
//		//		} else
//		//			return false;
//	}

//
//@StepComposant(ticker=StaticParameters._state_snapshot_frequency)
//public void notifyMyStateToGlobalServiceLeRetour(){
//	this.notify(new HostInfo());
//	this.mustDeclareFaulty=false;
//}
//
//public void notifyMyStateToGlobalServiceLeRetour(){
//	//		logMonologue(getMyCurrentState().getMyReliability()+" "+getMyCurrentState().getMyReplicas().isEmpty());
//	this.notify(new AgentInfo(),Laborantin.simulationResultStateObservationKey);
//}
//
//
//@MessageHandler
//@NotificationEnvelope
//public void systemInfoUpdate(final NotificationMessage<SystemInformationMessage> m) {
//	((CandidatureReplicaCore) this.myCore).beNotified(m.getNotification());
//	//		if (m.getSender().toString().equals("#HOST_MANAGER##simu_0#HostManager_0:77")&&m.getReceiver().toString().equals("#simu_0#DomainAgent_4"))
//	//			logMonologue(
//	//					"i've received "+m.getNotification()
//	//					+"\n myRelia="+getMyCurrentState().getMyReliability()+"("+getMyCurrentState().getMyStateStatus()+")"
//	//					+"\n aggregated="+((ReplicaCore) this.myCore).getFirstReliabilityTercile()+" , "+((ReplicaCore) this.myCore).getLastReliabilityTercile());
//
//	//		if (getMyCurrentState().getMyStateStatus().equals(AgentStateStatus.Wastefull))
//	//			logMonologue("I've becomed wastefull =o");
//	//		logMonologue("firstTercile="+((ReplicaCore) myCore).getFirstReliabilityTercile()
//	//				+"\n lastTercile="+((ReplicaCore) myCore).getLastReliabilityTercile()
//	//				+"\n my relia+status="+getMyCurrentState().getMyReliability()+" "+getMyCurrentState().getMyStateStatus());
//}
//


//
//Date protocolInitiationTime = null;
//AverageDoubleAggregator protoTime = new AverageDoubleAggregator();
//public Date lastAction = new Date();
//public boolean iAMDead=false;
//private boolean iveSarted=false;
//
//@Override
//public void initiateNegotiation(){
//	super.initiateNegotiation();
//	this.protocolInitiationTime = new Date();
//}
//
//@Override
//public void execute(final ReplicationCandidature c){
//	super.execute(c);
//	//		this.observe(c.getResource(), SystemInformationMessage.class);
//	if (this.protocolInitiationTime!=null){
//		this.protoTime.add( new Double(new Date().getTime() - this.protocolInitiationTime.getTime()));
//		this.protocolInitiationTime = null;
//	}
//	this.lastAction = new Date();
//	this.iveSarted=true;
//	this.iAMDead=false;
//
//}
//
///*
// *
// */
//
//@StepComposant(ticker=StaticParameters._quantileInfoFrequency)
//public void notifyMyStateToGlobalService(){
//	//		logMonologue(getMyCurrentState().getMyReliability()+" "+getMyCurrentState().getMyReplicas().isEmpty());
//	this.notify(new AgentInfo());
//}