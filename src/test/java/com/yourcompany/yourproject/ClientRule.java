package com.yourcompany.yourproject;

import static com.yourcompany.yourproject.support.Client.ClientBuilder.newClient;

import org.junit.rules.ExternalResource;

import com.yourcompany.yourproject.support.Client;

public class ClientRule extends ExternalResource {
    private Client client;
    private Object testInstance;
    
    public ClientRule(Object testInstance) {
        this.testInstance = testInstance;
    }

    @Override
    protected void before() throws Throwable {
        String applicationHostname = System.getProperty("application.hostname", "localhost");
        String applicationPort = System.getProperty("application.port", "8080");
        String applicationContext = System.getProperty("application.context", "/springdata");
        String baseUrl = "http://" + applicationHostname + ":" + applicationPort + (applicationContext.startsWith("/") ? "" : "/") + applicationContext;
        String webDriver = System.getProperty("selenium.webdriver", "firefox");
        int waitTimeInSeconds = Integer.parseInt(System.getProperty("selenium.waitTimeInSeconds", "10"));
        boolean followVisually = Boolean.parseBoolean(System.getProperty("selenium.follow.visually", "true"));

        client = newClient() //
                .baseUrl(baseUrl) //
                .webDriver(webDriver) //
                .waitTimeInSeconds(waitTimeInSeconds) //
                .followVisually(followVisually) //
                .onTest(testInstance) //
                .build();
    }
    
    public Client getClient() {
        return client;
    }

    @Override
    protected void after() {
        client.close();
    }
}