package name.monkey.slackjavaclient.model;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by tina on 10/30/15.
 */
public class ChannelsListResponse {
  @Key
  public boolean ok;

  @Key
  public List<SlackChannel> channels;
}
