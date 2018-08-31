package sommercamp.engler.chatserver;


import java.util.ArrayList;

public class ClientConnectionPool {
    private static ArrayList<ClientConnection> clientConnections = new ArrayList<ClientConnection>();

    private static int idCount = 0;

    public synchronized static void addConnection(ClientConnection clientConnection) {
        clientConnections.add(clientConnection);
        clientConnection.setConnectionId(getNextId());
        System.out.println("connection pool added: " + clientConnection.getConnectionId());
    }


    public synchronized static void removeById(int id){
        for(int i = 0; i < clientConnections.size(); i++)
            if(clientConnections.get(i).getConnectionId() == id){
                UserPool.removeConnectionByConnectionId(clientConnections.get(i).getConnectionId());
                clientConnections.remove(i);
                System.out.println("connection pool removed: " + id);
            }
    }

    public synchronized static int getNextId() {
        return idCount++;
    }
}
