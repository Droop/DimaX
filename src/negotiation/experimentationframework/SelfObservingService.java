package negotiation.experimentationframework;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import negotiation.faulttolerance.experimentation.ReplicationExperimentationProtocol;
import negotiation.negotiationframework.SimpleNegotiatingAgent;
import dima.basiccommunicationcomponents.Message;
import dima.introspectionbasedagents.annotations.MessageHandler;
import dima.introspectionbasedagents.annotations.PostStepComposant;
import dima.introspectionbasedagents.annotations.PreStepComposant;
import dima.introspectionbasedagents.annotations.ProactivityFinalisation;
import dima.introspectionbasedagents.annotations.StepComposant;
import dima.introspectionbasedagents.annotations.Transient;
import dima.introspectionbasedagents.services.BasicAgentCompetence;
import dima.introspectionbasedagents.services.core.loggingactivity.LogService;
import dima.introspectionbasedagents.shells.Ticker;

public abstract class SelfObservingService
extends BasicAgentCompetence<SimpleNegotiatingAgent<?, ?,?>>{
	private static final long serialVersionUID = 496384107474313690L;

	ActivityLog l = new ActivityLog();

	public SelfObservingService() {
		super();
	}


	//
	//
	//

	protected abstract ExperimentationResults generateMyResults();

	@StepComposant(ticker=ReplicationExperimentationProtocol._state_snapshot_frequency)
	public void notifyMyState(){
		l.add(this.generateMyResults());
		if (!getMyAgent().isAlive())//La vie est arrêté dans une step methode
			l.getResults().getLast().setLastInfo();
		//		this.notify(this.generateMyResults());
	}

	@ProactivityFinalisation()
	public void endSimulation(){
		this.logMonologue("this is the end my friend",LogService.onBoth);
		notify(l);
		getMyAgent().sendNotificationNow();
	}

	@MessageHandler
	public void simulationEndORder(final SimulationEndedMessage s){
		if (getMyAgent().isAlive()){
			getMyAgent().setAlive(false);
		}
	}

	//
	// Public class
	//

	public class ActivityLog extends Message {

		LinkedList<ExperimentationResults> results =
				new LinkedList<ExperimentationResults>();

		public LinkedList<ExperimentationResults> getResults() {
			return results;
		}

		public void add(ExperimentationResults generateMyResults) {
			results.add(generateMyResults);
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