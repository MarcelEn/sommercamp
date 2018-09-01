package sommercamp.engler.chatserver;


import java.util.ArrayList;

class ClientConnectionPool {
    private static ArrayList<ClientConnection> clientConnections = new ArrayList<ClientConnection>();

    private static int idCount = 0;

    synchronized static void addConnection(ClientConnection clientConnection) {
        clientConnections.add(clientConnection);
        clientConnection.setConnectionId(getNextId());
        System.out.println("connection pool added: " + clientConnection.getConnectionId());
    }


    synchronized static void removeById(int id){
        for(int i = 0; i < clientConnections.size(); i++)
            if(clientConnections.get(i).getConnectionId() == id){
                UserPool.removeConnectionByConnectionId(clientConnections.get(i).getConnectionId());
                clientConnections.remove(i);
                i--;
                System.out.println("connection pool removed: " + id);
            }
    }

    synchronized static int getNextId() {
        return idCount++;
    }
}
