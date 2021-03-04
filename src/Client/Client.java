package Client;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] ar) throws IOException {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(System.in);

        String address = "localhost";
        int serverPort = 8189;

        try {
            Socket socket;
            InetAddress ipAddress = InetAddress.getByName(address);
            System.out.println("Подключение к  " + address + ":" + serverPort + "...");
            while (true) {
                try {
                    socket = new Socket(ipAddress, serverPort);
                    break;
                } catch (ConnectException ignored) {
                }
            }

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String line;
            System.out.println("Связь установлена!");
            System.out.println();

            new Thread(() -> {
                while (true) {
                    try {
                        System.out.println(in.readUTF());
                    } catch (IOException e) {
                    }
                }
            }).start();

            while (true) {
                line = keyboard.readLine();
                out.writeUTF(line);
                out.flush();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
