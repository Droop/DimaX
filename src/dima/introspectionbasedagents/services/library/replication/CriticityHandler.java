package dima.introspectionbasedagents.services.library.replication;

import dima.introspectionbasedagents.CompetentComponent;
import dima.introspectionbasedagents.services.AgentCompetence;

public interface CriticityHandler<Agent extends CompetentComponent> extends AgentCompetence<Agent> {

	AgentCriticity getMyCriticity();


}
