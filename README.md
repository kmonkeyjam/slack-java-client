# slack-java-client
A simple slack java client that wraps the WebAPI.

### Configure the client:
- Create the applicationhttps://api.slack.com/applications
- Create a file src/main/resources/slack_client_secret.json.  An example can be found at src/main/resources/example_client_secret.json

### Example usage:
    SlackClient client = new SlackClient();
    SlackChannelHistoryResponse history = client.getHistory("C0000000");
    history.messages.stream().forEach(m -> System.out.println(m.text));
