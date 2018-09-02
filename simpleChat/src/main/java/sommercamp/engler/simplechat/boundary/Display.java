package sommercamp.engler.simplechat.boundary;

import sommercamp.engler.simplechat.service.Server;

public class Display extends Server {

    public Display(){
        super();

    }

    public void onMessage(String message) {
        System.out.println(message);
    }

    public void onError(Exception e) {
        e.printStackTrace();
    }
}
