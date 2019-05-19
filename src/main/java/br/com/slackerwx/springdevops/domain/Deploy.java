package br.com.slackerwx.springdevops.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "deploy")
public class Deploy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Id Long;
    private String componente;
    private String versao;
    private String responsavel;
    private String status;
    private Date data;

    public Deploy() {
        this.data = new Date();
    }

    public Id getLong() {
        return Long;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getData() {
        return data;
    }
}
