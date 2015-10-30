package name.monkey.slackjavaclient.model;

import com.google.api.client.util.Key;

/**
 * Created by tina on 10/30/15.
 */
public class SlackUser {
  @Key
  public String id;

  @Key
  public String name;

  @Key
  public SlackProfile profile;
}
