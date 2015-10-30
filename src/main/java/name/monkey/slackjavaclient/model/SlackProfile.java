package name.monkey.slackjavaclient.model;

import com.google.api.client.util.Key;

/**
 * Created by tina on 10/30/15.
 */
public class SlackProfile {
  @Key("first_name")
  public String firstName;

  @Key("last_name")
  public String lastName;
}
