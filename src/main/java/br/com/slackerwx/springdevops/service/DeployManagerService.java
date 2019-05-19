package br.com.slackerwx.springdevops.service;

import br.com.slackerwx.springdevops.domain.Deploy;
import br.com.slackerwx.springdevops.repository.DeployRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeployManagerService {

    @Autowired
    private DeployRepository deployRepository;

    public Deploy add(Deploy newDeploy) {
        return deployRepository.save(newDeploy);
    }
}
