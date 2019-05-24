package br.com.slackerwx.springdevops.controller;


import br.com.slackerwx.springdevops.dto.DeployDTO;
import br.com.slackerwx.springdevops.model.Deploy;
import br.com.slackerwx.springdevops.model.DeployTestUtil;
import br.com.slackerwx.springdevops.service.DeployManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeployController.class)
class DeployControllerTest {

    private static final Long DEPLOY_ID = 5L;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeployManagerService deployManagerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void newDeploy() throws Exception {
        DeployDTO created = DeployTestUtil.createDTO(DEPLOY_ID);
        Deploy persisted = DeployTestUtil.createModelObject(created);

        Mockito.when(deployManagerService.add(created)).thenReturn(persisted);

        mvc.perform(
                post("/api/v1/deploy")
                        .content(WebTestUtil.convertObjectToJsonBytes(created))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenDeployDto_whenGetDeploy_thenStatus200()
            throws Exception {

        DeployDTO created = DeployTestUtil.createDTO(DEPLOY_ID);
        Deploy persisted = DeployTestUtil.createModelObject(created);

        Mockito.when(deployManagerService.findById(created.getId())).thenReturn(persisted);

        mvc.perform(
                get("/api/v1/deploy/" + DEPLOY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responsavel").value(created.getResponsavel()));
    }

    @Test
    void givenDeploys_whenGetDeploys_thenStatus200() throws Exception {

        List<Deploy> persisteds = new ArrayList<>();

        for (long i = 0; i < 5; i++) {
            DeployDTO created = DeployTestUtil.createDTO(i);

            persisteds.add(DeployTestUtil.createModelObject(created));
        }

        Mockito.when(deployManagerService.findAll()).thenReturn(persisteds);

        mvc.perform(
                get("/api/v1/deploy/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].responsavel").value(persisteds.get(0).getResponsavel()));
    }

}