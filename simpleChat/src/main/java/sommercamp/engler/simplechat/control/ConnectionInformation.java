package sommercamp.engler.simplechat.control;

import lombok.Getter;

import java.util.Scanner;

@Getter
public
class ConnectionInformation {
    private int port = 0;
    private String address = null;

    private final int DEFAULT_PORT = 8080;

    private String DEFAULT_HOST = "localhost";

    public ConnectionInformation() {
        Scanner sc = new Scanner(System.in);
        String[] split;
        String input;
        while (port <= 0 || address == null) {
            try {
                System.out.println("Please Enter a valid Connection (IP:PORT) or press Enter for localhost:8080");
                input = sc.nextLine();
                if (input.length() == 0) {
                    address = DEFAULT_HOST;
                    port = DEFAULT_PORT;
                } else {
                    split = input.split(":");
                    address = validateIp(split[0]);
                    port = Integer.parseInt(split[1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String validateIp(String ip) throws Exception {
        String split[] = ip.split("\\.");
        if (split.length != 4)
            throw new Exception();

        for (int i = 0; i < 4; i++)
            if (Integer.parseInt(split[i]) < 0 || Integer.parseInt(split[i]) > 255)
                throw new Exception();

        return ip;
    }

    public String toString() {
        return address + ":" + port;
    }
}
