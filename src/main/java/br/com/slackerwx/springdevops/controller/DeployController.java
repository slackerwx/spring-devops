package br.com.slackerwx.springdevops.controller;

import br.com.slackerwx.springdevops.dto.DeployDTO;
import br.com.slackerwx.springdevops.model.Deploy;
import br.com.slackerwx.springdevops.service.DeployManagerService;
import br.com.slackerwx.springdevops.service.DeployNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class DeployController {

    @Autowired
    private DeployManagerService deployManagerService;

    @PostMapping("/deploy")
    DeployDTO newDeploy(@RequestBody DeployDTO newDeploy) {
        Deploy deploy = deployManagerService.add(newDeploy);

        return constructObject(deploy);
    }

    @GetMapping("/deploy/{id}")
    DeployDTO getDeploy(@PathVariable("id") Long id) throws DeployNotFoundException {
        Deploy deploy = deployManagerService.findById(id);

        return constructObject(deploy);
    }

    //TODO getDeploys (pagination)
    @GetMapping("/deploy/list")
    public List<DeployDTO> getDeploys() {
        List<Deploy> deploys = deployManagerService.findAll();

        return constructDeployList(deploys);
    }

    private List<DeployDTO> constructDeployList(List<Deploy> deploys) {
        return deploys
                .stream()
                .map(this::constructObject)
                .collect(Collectors.toList());
    }

    private DeployDTO constructObject(Deploy deploy) {
        DeployDTO object = new DeployDTO();

        object.setId(deploy.getId());
        object.setComponente(deploy.getComponente());
        object.setVersao(deploy.getVersao());
        object.setResponsavel(deploy.getResponsavel());
        object.setStatus(deploy.getStatus());
        object.setData(deploy.getData());

        return object;
    }
}
