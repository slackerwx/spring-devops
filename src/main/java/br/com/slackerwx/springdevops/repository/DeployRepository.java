package br.com.slackerwx.springdevops.repository;

import br.com.slackerwx.springdevops.model.Deploy;
import org.springframework.data.repository.CrudRepository;

public interface DeployRepository extends CrudRepository<Deploy, Long> {

}
