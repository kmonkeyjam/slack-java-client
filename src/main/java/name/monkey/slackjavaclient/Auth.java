package name.monkey.slackjavaclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import name.monkey.slackjavaclient.model.ClientSecrets;

import java.io.InputStream;
import java.util.Arrays;

public class Auth {
  private static final java.io.File DATA_STORE_DIR = new java.io.File(
          System.getProperty("user.home"), ".credentials/slack-java-client");
  private static final String SCOPE = "read";
  private static FileDataStoreFactory DATA_STORE_FACTORY;
  private static HttpTransport HTTP_TRANSPORT;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private static final JsonFactory JSON_FACTORY = new JacksonFactory();

  private static final String TOKEN_SERVER_URL = "https://slack.com/api/oauth.access";
  private static final String AUTHORIZATION_SERVER_URL =
          "https://slack.com/oauth/authorize";

  static {
    try {
      HTTP_TRANSPORT = new NetHttpTransport();
      DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
    } catch (Throwable t) {
      t.printStackTrace();
      System.exit(1);
    }
  }

  public static Credential authorize() throws Exception {
    InputStream in = Auth.class.getResourceAsStream("/slack_client_secret.json");
    ClientSecrets secrets = objectMapper.readValue(in, ClientSecrets.class);

    // set up authorization code flow
    AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken
            .authorizationHeaderAccessMethod(),
            HTTP_TRANSPORT,
            JSON_FACTORY,
            new GenericUrl(TOKEN_SERVER_URL),
            new ClientParametersAuthentication(
                    secrets.getClient_id(), secrets.getClient_secret()),
            secrets.getClient_id(),
            AUTHORIZATION_SERVER_URL).setScopes(Arrays.asList(SCOPE))
            .setDataStoreFactory(DATA_STORE_FACTORY).build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost(
            secrets.getDomain()).setPort(secrets.getPort()).build();
    System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
    Credential credential = flow.loadCredential("user");
    if (credential != null) {
      return credential;
    } else {
      return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
  }
}