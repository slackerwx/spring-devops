package br.com.slackerwx.springdevops.model;

import br.com.slackerwx.springdevops.dto.DeployDTO;

public class DeployTestUtil {

    public static DeployDTO createDTO(long id) {
        DeployDTO deploy = new DeployDTO();

        deploy.setId(id);
        deploy.setComponente("componente " + id);
        deploy.setVersao("versao " + id);
        deploy.setResponsavel("responsavel " + id);
        deploy.setStatus("status " + id);

        return deploy;
    }

    public static Deploy createModelObject(DeployDTO newDeploy) {
        return Deploy.getBuilder(
                newDeploy.getComponente(),
                newDeploy.getVersao(),
                newDeploy.getResponsavel(),
                newDeploy.getStatus()
        ).build();
    }

    public static Deploy createModelObject(Long id, String componente, String versao, String responsavel, String status) {
        return Deploy.getBuilder(
                componente,
                versao,
                responsavel,
                status
        ).build();
    }
}
