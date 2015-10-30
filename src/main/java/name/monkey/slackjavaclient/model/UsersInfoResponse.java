package name.monkey.slackjavaclient.model;

import com.google.api.client.util.Key;

/**
 * Created by tina on 10/30/15.
 */
public class UsersInfoResponse {
  @Key
  public boolean ok;

  @Key
  public SlackUser user;
}
