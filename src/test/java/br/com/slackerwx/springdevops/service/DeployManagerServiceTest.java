package br.com.slackerwx.springdevops.service;

import br.com.slackerwx.springdevops.dto.DeployDTO;
import br.com.slackerwx.springdevops.model.Deploy;
import br.com.slackerwx.springdevops.model.DeployTestUtil;
import br.com.slackerwx.springdevops.repository.DeployRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class DeployManagerServiceTest {

    private static final Long DEPLOY_ID = 5L;

    @Mock
    private DeployRepository deployRepositoryMock;

    @InjectMocks
    private DeployManagerService deployManagerService;

    @Test
    void save() {
        DeployDTO created = DeployTestUtil.createDTO(DEPLOY_ID);
        Deploy persisted = DeployTestUtil.createModelObject(created);

        Mockito.lenient().when(deployRepositoryMock.save(any(Deploy.class))).thenReturn(persisted);

        Deploy returned = deployManagerService.add(created);

        assertNotNull(returned);

        assertEquals(persisted.getResponsavel(), returned.getResponsavel());
    }

    @Test
    void findById() throws DeployNotFoundException {
        DeployDTO created = DeployTestUtil.createDTO(DEPLOY_ID);
        Deploy persisted = DeployTestUtil.createModelObject(created);

        Mockito.lenient().when(deployRepositoryMock.findById(DEPLOY_ID)).thenReturn(Optional.of(persisted));

        Deploy returned = deployManagerService.findById(DEPLOY_ID);

        assertEquals(persisted.getResponsavel(), returned.getResponsavel());
    }

    @Test
    void findByIdNotFound(){
        try {
            deployManagerService.findById(DEPLOY_ID);
        } catch (DeployNotFoundException e) {
            assertEquals("No deploy found with id " + DEPLOY_ID, e.getMessage());
        }
    }


    @Test
    void findAll() {
        List<Deploy> persisteds = new ArrayList<>();

        for (long i = 0; i < DEPLOY_ID; i++) {
            DeployDTO created = DeployTestUtil.createDTO(i);

            persisteds.add(DeployTestUtil.createModelObject(created));
        }

        Mockito.lenient().when(deployRepositoryMock.findAll()).thenReturn(persisteds);

        List<Deploy> returned = deployManagerService.findAll();

        assertEquals(persisteds, returned);
    }
}