package br.com.slackerwx.springdevops.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "deploy")
public class Deploy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String componente;
    private String versao;
    private String responsavel;
    private String status;
    private Date data;

    public static Builder getBuilder(String componente, String versao, String responsavel, String status){
        return new Builder(componente, versao, responsavel, status);
    }

    public static class Builder {
        Deploy built;

        Builder(String componente, String versao, String responsavel, String status){
            built = new Deploy();
            built.componente = componente;
            built.versao = versao;
            built.responsavel = responsavel;
            built.status = status;
            built.data = new Date();
        }

        public Deploy build(){
            return built;
        }
    }
}
