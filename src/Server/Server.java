package Server;

import java.net.*;
import java.io.*;
public class Server {

    public static void main(String[] ar) {
        int port = 8189;
        try {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Ожидание подключения...");


            Socket socket = ss.accept();
            System.out.println("Связь установлена!");
            System.out.println();

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line;

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
