package name.monkey.slackjavaclient;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import name.monkey.slackjavaclient.model.ChannelHistoryResponse;
import name.monkey.slackjavaclient.model.UsersInfoResponse;

import java.io.IOException;

/**
 * Created by tina on 10/30/15.
 */
public class SlackClient {
  private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();
  private Credential credential;
  private boolean debug = false;
  
  public static class Param {
    private String name;
    private String value;

    public Param(String name, String value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public String getValue() {
      return value;
    }
  }

  public SlackClient() throws Exception {
    this.credential = Auth.authorize();
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  private HttpResponse run(String url) throws IOException {
    if (debug) System.out.println("Fetching: " + url);
    HttpRequestFactory requestFactory =
            HTTP_TRANSPORT.createRequestFactory(request -> {
              request.setParser(new JsonObjectParser(JSON_FACTORY));
            });
    HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
    return request.execute();
  }

  public ChannelHistoryResponse getHistory(String channelId, Param... optional) throws IOException {
    String apiUrl = "https://slack.com/api/channels.history";
    StringBuilder builder = getBaseUrlBuilder(apiUrl, optional)
            .append("&channel=")
            .append(channelId);

    HttpResponse response = run(builder.toString());
    return response.parseAs(ChannelHistoryResponse.class);
  }

  public UsersInfoResponse getUserInfo(String userId, Param... optional) throws IOException {
    String apiUrl = "https://slack.com/api/users.info";
    StringBuilder builder = getBaseUrlBuilder(apiUrl, optional)
            .append("&user=")
            .append(userId);

    HttpResponse response = run(builder.toString());
    return response.parseAs(UsersInfoResponse.class);
  }

  private StringBuilder getBaseUrlBuilder(String apiUrl, Param[] optional) {
    StringBuilder builder = new StringBuilder()
            .append(apiUrl)
            .append("?token=")
            .append(credential.getAccessToken());

    for (Param param : optional) {
      builder.append("&");
      builder.append(param.getName());
      builder.append("=");
      builder.append(param.getValue());
    }
    return builder;
  }
}
