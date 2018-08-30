package sommercamp.engler.chatclient;

import sommercamp.engler.modules.Action;

public interface ConnectionRequirements {
    void onMessage(Action action);
    void onServerDisconnect();
}
