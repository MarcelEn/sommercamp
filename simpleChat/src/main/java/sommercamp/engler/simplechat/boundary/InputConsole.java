package sommercamp.engler.simplechat.boundary;

import sommercamp.engler.simplechat.service.Client;

public class InputConsole extends Client {

    public InputConsole(){
        super();
        while(true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendMessage("send to Server");
        }
    }

    public void onMessage(String message) {
        System.out.println(message);
    }

    public void onError(Exception e) {
        e.printStackTrace();
    }
}
