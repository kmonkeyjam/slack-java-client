package name.monkey.slackjavaclient.model;

import com.google.api.client.util.Key;

/**
 * Created by tina on 10/30/15.
 */
public class SlackMessage {
  @Key
  public String type;

  @Key
  public String ts;

  @Key
  public String user;

  @Key
  public String text;
}
