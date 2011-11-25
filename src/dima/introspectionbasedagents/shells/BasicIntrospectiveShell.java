package dima.introspectionbasedagents.shells;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.lang.annotation.Annotation;


import dima.basicinterfaces.DimaComponentInterface;
import dima.introspectionbasedagents.annotations.PostStepComposant;
import dima.introspectionbasedagents.annotations.PreStepComposant;
import dima.introspectionbasedagents.annotations.ProactivityFinalisation;
import dima.introspectionbasedagents.annotations.ProactivityInitialisation;
import dima.introspectionbasedagents.annotations.StepComposant;
import dima.support.GimaObject;

public class BasicIntrospectiveShell extends GimaObject {

	private static final long serialVersionUID = -8399072656535198387L;

	//
	// Fields
	//

	//	private final DimaComponentInterface myMainComponent;

	/** The,agent and its methods **/
	private IntrospectedMethodsTrunk myMethods;

	/** The Handler called if an exception occurs **/
	private final SimpleExceptionHandler exceptionHandler;

	//
	// Constructor
	//

	public BasicIntrospectiveShell(
			final DimaComponentInterface myComponent, 
			final IntrospectedMethodsTrunk methods) {
		super();
		this.myMethods = methods;
		this.exceptionHandler  = new SimpleExceptionHandler();

		this.myMethods.load(myComponent);
	}

	public BasicIntrospectiveShell(
			final DimaComponentInterface myComponent, 
			final IntrospectedMethodsTrunk methods,
			final SimpleExceptionHandler exceptionHandler) {
		super();
		this.myMethods = methods;
		this.exceptionHandler = exceptionHandler;

		this.myMethods.load(myComponent);
	}

	/*
	 *
	 */

	public BasicIntrospectiveShell(
			final DimaComponentInterface myComponent) {
		super();
		this.myMethods = new BasicIntrospectedMethodsTrunk();
		this.exceptionHandler  = new SimpleExceptionHandler();

		this.myMethods.load(myComponent);
	}

	public BasicIntrospectiveShell(
			final DimaComponentInterface myComponent, final Date horloge,
			final SimpleExceptionHandler exceptionHandler) {
		super();
		this.myMethods = new BasicIntrospectedMethodsTrunk();
		this.exceptionHandler = exceptionHandler;

		this.myMethods.load(myComponent);
	}

	//
	// Accessors
	//

	/**
	 * @return the exceptionHandler
	 */
	public SimpleExceptionHandler getExceptionHandler() {
		return this.exceptionHandler;
	}

	/**
	 * @return the status
	 */
	public SimpleAgentStatus getStatus() {
		return this.getMyMethods().getStatus();
	}

	/*
	 *
	 */

	public IntrospectedMethodsTrunk getMyMethods() {
		return this.myMethods;
	}

	public void setMyMethods(final IntrospectedMethodsTrunk myMethods) {
		this.myMethods = myMethods;
	}

	/*
	 *
	 */


	//
	// Methods
	//
	
	public final void proactivityInitialize(Date creation){
		this.executeBehaviors(ProactivityInitialisation.class, creation);

	}

	public final void preActivity(Date creation){
		this.executeBehaviors(PreStepComposant.class, creation);

	}

	public void step(Date creation){
		this.executeBehaviors(StepComposant.class, creation);

	}

	protected final Set<MethodHandler> metToRemove = new HashSet<MethodHandler>();
	public final void postActivity(Date creation){
		this.executeBehaviors(PostStepComposant.class, creation);
		for (final MethodHandler meth : this.metToRemove)
			this.myMethods.removeMethod(meth);
		this.metToRemove.clear();
	}

	public void proactivityTerminate(Date creation){
		this.executeBehaviors(ProactivityFinalisation.class, creation);

	}

	//
	// Primitive
	//


	/**
	 * Execute all the reflective methods of ag
	 *
	 * @param myComponent
	 *            the agent to execute
	 */
	protected void executeBehaviors(Class<? extends Annotation> annotation, Date creation) {
		for (final MethodHandler mt : this.myMethods.getMethods()){
			if (mt.isAnnotationPresent(annotation))
				try {
					final boolean toRemove = this.myMethods.executeStepMethod(mt, creation);
					if (toRemove)
						this.metToRemove.add(mt);
				} catch (final Throwable e) {
					// The exception is raised by the method
					this.getExceptionHandler().handleException(
							e, this.getStatus());
				}
		}

	}
}
