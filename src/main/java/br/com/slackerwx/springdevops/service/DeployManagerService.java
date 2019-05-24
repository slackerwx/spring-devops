package br.com.slackerwx.springdevops.service;

import br.com.slackerwx.springdevops.dto.DeployDTO;
import br.com.slackerwx.springdevops.model.Deploy;
import br.com.slackerwx.springdevops.repository.DeployRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeployManagerService {

    private final DeployRepository deployRepository;

    @Autowired
    public DeployManagerService(DeployRepository deployRepository) {
        this.deployRepository = deployRepository;
    }

    public Deploy add(DeployDTO newDeploy) {

        Deploy deploy = Deploy.getBuilder(
                newDeploy.getComponente(),
                newDeploy.getVersao(),
                newDeploy.getResponsavel(),
                newDeploy.getStatus()
        ).build();

        return deployRepository.save(deploy);
    }

    public Deploy findById(Long deployId) throws DeployNotFoundException {
        return deployRepository
                .findById(deployId)
                .orElseThrow(() -> new DeployNotFoundException("No deploy found with id " + deployId));
    }

    public List<Deploy> findAll() {
        return (List<Deploy>) deployRepository.findAll();
    }

}
