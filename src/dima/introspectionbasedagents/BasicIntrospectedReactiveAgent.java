package dima.introspectionbasedagents;

import java.util.Date;

import dima.basicagentcomponents.AgentIdentifier;
import dima.introspectionbasedagents.shells.BasicIntrospectiveShell;
import dima.kernel.BasicAgents.BasicReactiveAgent;

public abstract class BasicIntrospectedReactiveAgent extends BasicReactiveAgent {
	private static final long serialVersionUID = -4254700016192507990L;

	private final Date creation;
	private BasicIntrospectiveShell myShell;

	public BasicIntrospectedReactiveAgent() {
		super();
		this.creation = new Date();
	}

	public BasicIntrospectedReactiveAgent(final AgentIdentifier newId) {
		super(newId);
		this.creation = new Date();
		this.myShell = new BasicIntrospectiveShell(this);
	}

	public BasicIntrospectedReactiveAgent(final String newId) {
		super(newId);
		this.creation = new Date();
		this.myShell = new BasicIntrospectiveShell(this);
	}


	public BasicIntrospectedReactiveAgent(final Date horloge) {
		super();
		this.creation = horloge;
		this.myShell = new BasicIntrospectiveShell(this);
	}

	public BasicIntrospectedReactiveAgent(final AgentIdentifier newId, final Date horloge) {
		super(newId);
		this.creation = horloge;
		this.myShell = new BasicIntrospectiveShell(this);
	}

	public BasicIntrospectedReactiveAgent(final String newId, final Date horloge) {
		super(newId);
		this.creation = horloge;
		this.myShell = new BasicIntrospectiveShell(this);
	}

	//
	// Proactivity
	//


	@Override
	public void proactivityInitialize() {
		this.myShell =initiateMyShell();
		myShell.proactivityInitialize(creation);
		Thread.yield();
	}

	@Override
	public void preActivity() {
		myShell.preActivity(creation);
			Thread.yield();
	}

	@Override
	public final void step() {
			this.myShell.step(creation);
	}

	@Override
	public void postActivity(){
		this.myShell.postActivity(creation);
			Thread.yield();
	}
	
	@Override
	public void proactivityTerminate() {
		myShell.proactivityTerminate(creation);
		Thread.yield();
	}
	
	//
	// Primitive
	//
	
	protected BasicIntrospectiveShell initiateMyShell(){
		return  new BasicIntrospectiveShell(this);
	}
}
