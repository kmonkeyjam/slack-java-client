package name.monkey.slackjavaclient.model;

import com.google.api.client.util.Key;

/**
 * Created by tina on 10/30/15.
 */
public class SlackMessage {
  @Key
  public String type;

  @Key("ts")
  public String timestamp;

  @Key("user")
  public String userId;

  @Key
  public String text;
}
