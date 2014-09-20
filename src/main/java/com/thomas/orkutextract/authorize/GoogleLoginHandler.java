package com.thomas.orkutextract.authorize;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.orkut.OrkutScopes;
import com.thomas.orkutextract.config.Inputs;
import com.thomas.orkutextract.exception.OrkutLoginException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Created by tx00375 on 9/19/2014.
 */
public class GoogleLoginHandler {

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    public static final String APPLICATION_NAME = Inputs.APP_NAME;
    /**
     * Credential of logged in user
     */
    private static Credential credential;
    /**
     * Global instance of the {@link com.google.api.client.util.store.DataStoreFactory}. The best practice is to make it a single
     * globally shared instance across your application.
     */
    public static FileDataStoreFactory DATA_STORE_FACTORY = initDataStoreFactory();
    /**
     * Global instance of the HTTP transport.
     */
    public static HttpTransport HTTP_TRANSPORT = initHttpTransport();
    /**
     * Global instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();


    private static GoogleLoginHandler ourInstance = new GoogleLoginHandler();

    public static GoogleLoginHandler getInstance() {
        return ourInstance;
    }

    private GoogleLoginHandler() {

        try {
            authorize();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Authorizes the installed application to access user's protected data.
     */
    private static void authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(GoogleLoginHandler.class.getResourceAsStream("/client_secrets.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=plus "
                            + "into plus-cmdline-sample/src/main/resources/client_secrets.json"
            );
            System.exit(1);
        }
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
                Collections.singleton(OrkutScopes.ORKUT)).setDataStoreFactory(
                DATA_STORE_FACTORY).build();
        // authorize
        credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public Credential getCredential(){return credential;}

    private static FileDataStoreFactory initDataStoreFactory() {
        if (null == DATA_STORE_FACTORY) {
            /** Directory to store user credentials. */
            java.io.File dataStoreDir = new java.io.File(System.getProperty("user.home"), Inputs.USER_CRED_DIR);
            try {
                DATA_STORE_FACTORY = new FileDataStoreFactory(dataStoreDir);
            } catch (IOException e) {
                throw new OrkutLoginException(e);
            }
        }
        return DATA_STORE_FACTORY;
    }

    private static HttpTransport initHttpTransport() {
        if (HTTP_TRANSPORT == null) {
            try {
                HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            } catch (GeneralSecurityException | IOException  e) {
                throw new OrkutLoginException(e);
            }
        }
        return HTTP_TRANSPORT;
    }
}
