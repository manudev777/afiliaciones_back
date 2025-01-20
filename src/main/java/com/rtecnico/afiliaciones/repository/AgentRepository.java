package com.rtecnico.afiliaciones.repository;

import com.rtecnico.afiliaciones.model.Agent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Long> {
}
