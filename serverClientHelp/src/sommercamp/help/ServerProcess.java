package sommercamp.help;

import sommercamp.help.server.ClientConnection;
import sommercamp.help.server.ClientConnectionMethods;
import sommercamp.help.server.Server;
import sommercamp.help.server.ServerMethods;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerProcess implements ServerMethods, ClientConnectionMethods {
    private int PORT = 8080;
    ArrayList<ClientConnection> clientConnections = new ArrayList<>();

    ServerProcess(){
        try {
            new Server(8080, this::onConnection, this::onClientError);
            System.out.println("listening on " + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized ArrayList<ClientConnection> handleArray(SyncronizedAction action, ClientConnection clientConnection){
        switch (action){
            case ADD:
                clientConnections.add(clientConnection);
                return null;

            case REMOVE:
                for(int i = 0; i < clientConnections.size(); i ++)
                    if(clientConnections.get(i).getConnectionId() == clientConnection.getConnectionId()){
                        clientConnections.remove(i);
                        return null;
                    }
                return null;

            case GET:
                return (ArrayList<ClientConnection>) clientConnections.clone();
        }
        return null;
    }

    @Override
    public void onClientError(IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onServerError(IOException e) {
        e.printStackTrace();
    }

    // Server Methods
    @Override
    public void onConnection(Socket clientSocket) {
        System.out.println("got a new Connection: " + clientSocket);
        try {
            handleArray(SyncronizedAction.ADD, new ClientConnection(clientSocket, this::onMessage, this::onDisconnect));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Handle User Actions
    @Override
    public void onDisconnect(ClientConnection clientConnection) {
        handleArray(SyncronizedAction.REMOVE, clientConnection);
    }

    @Override
    public void onMessage(ClientConnection clientConnection, String message) {
        System.out.println("Received: " + message);
        try {
            clientConnection.send(message.toUpperCase());
        } catch (IOException e) {
            onDisconnect(clientConnection);
            e.printStackTrace();
        }
    }
}
