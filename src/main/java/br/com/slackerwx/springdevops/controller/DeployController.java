package br.com.slackerwx.springdevops.controller;

import br.com.slackerwx.springdevops.domain.Deploy;
import br.com.slackerwx.springdevops.service.DeployManagerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeployController {

    private DeployManagerService deployManagerService;

    @PostMapping("/deploy")
    Deploy newDeploy(@RequestBody Deploy newDeploy){
        return deployManagerService.add(newDeploy);
    }
}
