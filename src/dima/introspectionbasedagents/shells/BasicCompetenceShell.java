package dima.introspectionbasedagents.shells;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dima.basiccommunicationcomponents.AbstractMailBox;
import dima.basicinterfaces.ActiveComponentInterface;
import dima.basicinterfaces.MailBoxBasedCommunicatingComponentInterface;
import dima.introspectionbasedagents.BasicCompetentAgent;
import dima.introspectionbasedagents.CommunicatingCompetentComponent;
import dima.introspectionbasedagents.CompetentComponent;
import dima.introspectionbasedagents.annotations.Competence;
import dima.introspectionbasedagents.services.AgentCompetence;
import dima.introspectionbasedagents.services.BasicAgentCompetence;
import dima.introspectionbasedagents.services.DuplicateCompetenceException;
import dima.introspectionbasedagents.services.UnInstanciedCompetenceException;
import dima.introspectionbasedagents.services.UnrespectedCompetenceSyntaxException;
import dima.introspectionbasedagents.services.core.loggingactivity.LogService;
import dima.introspectionbasedagents.services.core.observingagent.PatternObserverWithHookservice;

/**
 * The competence shell adds the handle of competences to an agent introspective shell
 *
 * @author Sylvain Ductor
 */
public class BasicCompetenceShell<Agent extends CommunicatingCompetentComponent & MailBoxBasedCommunicatingComponentInterface>  extends
BasicCommunicatingShell {
	private static final long serialVersionUID = 87670953420119714L;

	//
	// Field
	//

	Agent myMainComponent;
	Collection<Class<? extends AgentCompetence<Agent>>> loadedCompetence = 
			new ArrayList<Class<? extends AgentCompetence<Agent>>>();

	//
	// Constructor
	//

	public BasicCompetenceShell(
			final Agent myComponent, final Date horloge,
			final AbstractMailBox mailbox,
			final LogService exceptionHandler) 
					throws UnInstanciedCompetenceException, DuplicateCompetenceException, UnrespectedCompetenceSyntaxException{
		super(myComponent, horloge, mailbox, exceptionHandler);
		getExceptionHandler().setMyAgentShell(this);
		
		myMainComponent = myComponent;
		for (AgentCompetence<Agent> comp : getNativeCompetences(myMainComponent)){
			load(comp);
		}
				if (myMainComponent instanceof BasicCompetentAgent) {
					BasicCompetentAgent agent = (BasicCompetentAgent) myMainComponent;
					PatternObserverWithHookservice.registerEventMethod(agent, agent.observer);
		
				}
	}
	public BasicCompetenceShell(
			final Agent myComponent, final Date horloge,
			final AbstractMailBox mailbox) 
					throws UnInstanciedCompetenceException, DuplicateCompetenceException, UnrespectedCompetenceSyntaxException{
		super(myComponent, horloge, mailbox, new LogService(myComponent));
		for (AgentCompetence<Agent> comp : getNativeCompetences(myMainComponent)){
			load(comp);
		}
				if (myMainComponent instanceof BasicCompetentAgent) {
					BasicCompetentAgent agent = (BasicCompetentAgent) myMainComponent;
					PatternObserverWithHookservice.registerEventMethod(agent, agent.observer);
		
				}
	}

	public BasicCompetenceShell(final Agent myComponent, final Date horloge) 
			throws UnInstanciedCompetenceException, DuplicateCompetenceException, UnrespectedCompetenceSyntaxException {
		this(myComponent, horloge, myComponent.getMailBox(), new LogService(myComponent));
	}

	//
	// Methods
	//

	@Override
	@SuppressWarnings("unchecked")
	public LogService<Agent> getExceptionHandler(){
		return (LogService<Agent>) super.getExceptionHandler();
	}
	@SuppressWarnings("unchecked")
	public void load(AgentCompetence<Agent> competence)
			throws UnInstanciedCompetenceException, DuplicateCompetenceException, UnrespectedCompetenceSyntaxException{
		if (competence==null){
			LogService.writeException(this,
					"This competence '"+competence
					+"' has not been instanciated, it can not be used");
			throw new UnInstanciedCompetenceException(competence.toString());
		}

		for (Class<? extends AgentCompetence<Agent>> compAlre : loadedCompetence)
			if (compAlre.isAssignableFrom(competence.getClass()) 
					|| competence.getClass().isAssignableFrom(compAlre))
				throw new DuplicateCompetenceException(competence+" & "+compAlre);

				//OK
				loadedCompetence.add((Class<? extends AgentCompetence<Agent>>) competence.getClass());
				competence.setMyAgent(myMainComponent);
				this.getMyMethods().load(competence);
	}

	public void unload(AgentCompetence<Agent> newComp) {
		throw new RuntimeException("todo : parcout des méthodes de 'getMyMethods()' " +
				"et suppression de tous les méthod handler dont le caller est newComp + suppression de loadedCompetence" +
				"attention a mise a jour des hook; restriction au comp non native (attribut)??+ passage de l'etat de l'agent a faulty");
	}
	//
	// Behavior
	//


	@Override
	public final void proactivityTerminate(){
		super.proactivityTerminate();
		try {

			for (final AgentCompetence<? extends CompetentComponent> competence : getNativeCompetences(myMainComponent))
				competence.die();

		} catch (Exception e) {
			LogService.writeException(this,"proactivityTerminate : Impossible!!");
		}

		this.myMainComponent=null;
	}


	Map<MethodHandler, MethodHandler> methodsHook =
			new HashMap<MethodHandler, MethodHandler>();

	/*
	 * Hooks
	 */

	/**
	 * Used to add a hook
	 *
	 * @param testComp
	 * @param testMethod
	 * @param testMethodSignature
	 * @param testMethodArguments
	 * @param executionMethod
	 * @param executionMethodSignature
	 * @param executionMethodArgument
	 * @return false if a hook already existed for this key
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 */
	public Boolean addHook(
			final ActiveComponentInterface testComp,
			final String testMethod,
			final Class<?>[] testMethodSignature,
			final Object[] testMethodArguments,
			final ActiveComponentInterface execComp,
			final String executionMethod,
			final Class<?>[] executionMethodSignature,
			final Object[] executionMethodArgument)
					throws SecurityException, IllegalArgumentException, NoSuchMethodException
					{

		final MethodHandler compTestMethod = new MethodHandler(
				testComp,
				testMethod, testMethodSignature, testMethodArguments);
		final MethodHandler agExecMethod = new MethodHandler(
				execComp,
				executionMethod, executionMethodSignature, executionMethodArgument);

		return this.methodsHook.put(
				compTestMethod,
				agExecMethod) == null;
					}

	public void executeHooks(){
		try {
			for (final MethodHandler compM : this.methodsHook.keySet()){
				final Object resultatTest = compM.execute();
				if (resultatTest instanceof Boolean && (Boolean) resultatTest){
					final Object resultatAg = this.methodsHook.get(compM).execute();
					if (resultatAg instanceof Boolean && (Boolean) resultatAg)
						this.methodsHook.remove(compM);
				}
			}
		} catch (final Throwable e) {
			((LogService) this.getExceptionHandler()).handleExceptionOnHooks(e, this.getStatus());
		}
	}

	//
	// Primitive
	//

	@SuppressWarnings("unchecked")
	public static <SAgent extends CompetentComponent> Collection<AgentCompetence<SAgent>> getNativeCompetences(SAgent mainComponent) 
			throws UnrespectedCompetenceSyntaxException {
		Collection<AgentCompetence<SAgent>> result = new ArrayList<AgentCompetence<SAgent>>();

		for (final Field comp : IntrospectionStaticPrimitivesLibrary.getAllFields(mainComponent.getClass()))
			if (fieldIsACompetence(comp)){
				//Important code a remettre
				//				if (!Modifier.isFinal(comp.getModifiers())){
				//					LoggerManager.writeWarning(this,
				//							"This competence '"+comp.getName()+"' is not final, it can not be used");
				//					throw new UnInstanciableCompetenceException(comp.getName());
				//	}

				AgentCompetence<SAgent>myComp=null;						
				try {							
					myComp = (AgentCompetence<SAgent>)comp.get(mainComponent);
				} catch (final Exception e) {
					LogService.writeException(mainComponent,
							"Impossible!! (voir fieldIsACompetence)", e);
					throw new RuntimeException(comp.getName());
				}

				if (myComp==null)
					throw new RuntimeException("wtf!!!!!"+comp);
				else
					result.add(myComp);
			}
		return result;
	}

	private static boolean fieldIsACompetence (final Field comp) 
			throws UnrespectedCompetenceSyntaxException{

		//		System.out.println("------------testing "+comp);

		if (comp.isAnnotationPresent(Competence.class))
			//			System.out.println("------------testing "+comp+ "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			if (AgentCompetence.class.isAssignableFrom(comp.getType()))
				return true;
			else{
				LogService.writeWarning(comp,
						"This field '"+comp.getName()
						+"' is annotated with competence but " +
						"does not implement AgentCompetence interface, " +
						"it can not be used");
				throw new UnrespectedCompetenceSyntaxException(comp.getName());
			}

		if (AgentCompetence.class.isAssignableFrom(comp.getType()))
			//			System.out.println("------------testing "+comp+ "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			if (comp.isAnnotationPresent(Competence.class))
				return true;
			else {
				LogService.writeWarning(comp,
						"This field '"+comp.getName()
						+"' implements AgentCompetence interface " +
						"is not annotated with competence, " +
						"it can not be used");
				throw new UnrespectedCompetenceSyntaxException(comp.getName());
			}

		return false;
	}
}
