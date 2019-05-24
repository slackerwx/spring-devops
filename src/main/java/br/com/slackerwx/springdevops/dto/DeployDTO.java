package br.com.slackerwx.springdevops.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DeployDTO {
    private Long id;
    private String componente;
    private String versao;
    private String responsavel;
    private String status;
    private Date data;
}
