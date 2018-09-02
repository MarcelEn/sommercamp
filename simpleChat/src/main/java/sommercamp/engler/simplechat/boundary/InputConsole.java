package sommercamp.engler.simplechat.boundary;

import sommercamp.engler.simplechat.service.Client;

public class InputConsole extends Client {

    public InputConsole(){
        super();
        sendMessage("hi");
        sendMessage("hi");
        sendMessage("hi");
        sendMessage("hi");
    }

    public void onMessage(String message) {
        System.out.println(message);
    }

    public void onError(Exception e) {
        e.printStackTrace();
    }
}
