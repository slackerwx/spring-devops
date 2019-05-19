package br.com.slackerwx.springdevops.service;

import br.com.slackerwx.springdevops.domain.Deploy;
import br.com.slackerwx.springdevops.repository.DeployRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeployManagerServiceTest {

    @Mock
    private DeployRepository deployRepository;

    @InjectMocks
    private DeployManagerService deployManagerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
        Deploy aMockDeploy = new Deploy();
        aMockDeploy.setComponente("componente");
        aMockDeploy.setVersao("versao");
        aMockDeploy.setResponsavel("responsavel");
        aMockDeploy.setStatus("status");

        when(deployRepository.save(any(Deploy.class))).thenReturn(aMockDeploy);

        Deploy deploy = deployManagerService.add(aMockDeploy);

        assertEquals("responsavel", deploy.getResponsavel());
    }
}