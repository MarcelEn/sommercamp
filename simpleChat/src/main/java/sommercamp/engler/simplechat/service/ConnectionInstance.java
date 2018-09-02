package sommercamp.engler.simplechat.service;

public interface ConnectionInstance {
    void onMessage(String message);
    void onError(Exception e);
    void sendMessage(String message);
}
