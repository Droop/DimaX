package negotiation.experimentationframework;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import negotiation.faulttolerance.experimentation.ReplicationExperimentationParameters;
import negotiation.negotiationframework.interaction.ResourceIdentifier;

import dima.basicagentcomponents.AgentIdentifier;
import dima.basicagentcomponents.AgentName;
import dima.basicinterfaces.DimaComponentInterface;
import dima.introspectionbasedagents.services.core.loggingactivity.LogService;

public abstract class ExperimentationParameters
implements DimaComponentInterface {

//	final AgentIdentifier experimentatorId;
	
	
	private final String simulationName = newName();
	private final File f;

	protected final AgentIdentifier experimentatorId;
	public final int nbAgents;
	public final int nbHosts;

	List<AgentIdentifier> replicasIdentifier  = new ArrayList<AgentIdentifier>();
	List<ResourceIdentifier> hostsIdentifier = new ArrayList<ResourceIdentifier>();


	public ExperimentationParameters(
			final File f,
			AgentIdentifier experimentatorId,
			final int nbAgents, final int nbHosts) {
		this.f = f;
		this.experimentatorId=experimentatorId;		
		this.nbAgents = nbAgents;
		this.nbHosts= nbHosts;
	}

	//
	// Accessors
	//

	public abstract long getMaxSimulationTime() ;

	public int getNbAgents() {
		return this.nbAgents;
	}

	public int getNbHosts() {
		return this.nbHosts;
	}

	public int getNbAgentsNHosts() {
		return this.nbAgents+this.nbHosts;
	}
	
	public List<AgentIdentifier> getReplicasIdentifier() {
		return this.replicasIdentifier;
	}

	public List<ResourceIdentifier> getHostsIdentifier() {
		return this.hostsIdentifier;
	}

	public String getName() {
		return this.getSimulationName();
	}

	//
	// Methods
	//

	public void initiate(){}
	
	public final void initiateParameters(){
		initiateAgentsAndHosts();
		initiate();
	}

	private void initiateAgentsAndHosts(){
		/*
		 * Agents and hosts names
		 */

		for (int i=0; i<this.nbAgents; i++)
			this.replicasIdentifier.add(//new AgentName("_--simu="+p.toString()+"--_DomainAgent_"+i));
					new AgentName("#"+this.getSimulationName()+"#DomainAgent_"+i));
		for (int i=0; i<this.nbHosts; i++)
			this.hostsIdentifier.add(//new ResourceIdentifier("_--simu="+p.toString()+"--_HostManager_"+i,77));
					new ResourceIdentifier("#"+this.getSimulationName()+"#HostManager_"+i,77));
	}

	
	/*
	 * 
	 */
	

	public abstract int numberOfTimePoints();

	public abstract int getTimeStep(final ExperimentationResults ag);
	
	public abstract Long geTime(final int i);

	//
	// Primitive
	//

	private static int id = -1;
	private static String newName(){
		id++;
		return "simu_"+id;
	}
	@Override
	public String toString(){
		String result ="****************************************************************************************************************************\n";
		result+= "Simulation with parameters :\n";
		for (final Field f : ReplicationExperimentationParameters.class.getFields())
			try {
				result += f.getName()+" : "+f.get(this)+"\n";
			} catch (final Exception e) {
				LogService.writeException("immmmmmmmpppppppppoooooooossssssssiiiiiiiiiibbbbllllllllllle",e);
			}
			result+="**************";
			return result;
	}

	public File getF() {
		return f;
	}

	public String getSimulationName() {
		return simulationName;
	}
}
