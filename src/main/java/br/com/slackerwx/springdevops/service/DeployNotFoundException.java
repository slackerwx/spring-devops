package br.com.slackerwx.springdevops.service;

public class DeployNotFoundException extends Exception {
    DeployNotFoundException(String s) {
        super(s);
    }
}
