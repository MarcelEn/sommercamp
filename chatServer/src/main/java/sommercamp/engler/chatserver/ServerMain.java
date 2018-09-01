package sommercamp.engler.chatserver;

import sommercamp.engler.chatserver.boundary.Server;

public class ServerMain {
    public static void main(String[] args){
        new Server(8080);
    }
}
