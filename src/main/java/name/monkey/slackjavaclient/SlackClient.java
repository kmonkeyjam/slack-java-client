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
import name.monkey.slackjavaclient.model.SlackChannelHistoryResponse;

import java.io.IOException;

/**
 * Created by tina on 10/30/15.
 */
public class SlackClient {
  private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();
  private Credential credential;

  public SlackClient() throws Exception {
    this.credential = Auth.authorize();
  }

  private HttpResponse run(String url) throws IOException {
    HttpRequestFactory requestFactory =
            HTTP_TRANSPORT.createRequestFactory(request -> {
              request.setParser(new JsonObjectParser(JSON_FACTORY));
            });
    HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
    return request.execute();
  }

  public SlackChannelHistoryResponse getHistory(String channelId) throws IOException {
    String url = new StringBuilder()
            .append("https://slack.com/api/channels.history")
            .append("?token=")
            .append(credential.getAccessToken())
            .append("&channel=")
            .append(channelId)
            .toString();

    HttpResponse response = run(url);
    return response.parseAs(SlackChannelHistoryResponse.class);
  }
}
