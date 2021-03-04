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
            InetAddress ipAddress = InetAddress.getByName(address); //цепляю адрес
            System.out.println("Подключение к  " + address + ":" + serverPort + "...");
            while (true) {
                try {
                    socket = new Socket(ipAddress, serverPort); //цепляю адрес и порт на сокет
                    break;
                } catch (ConnectException ignored) {
                }
            }
            DataInputStream in = new DataInputStream(socket.getInputStream()); //поток сюда
            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); //поток отсюда
            String line; 
            System.out.println("Связь установлена!");
            System.out.println();

            new Thread(() -> { //делаю отдельный поток на входящие сообщения
                while (true) {
                    try {
                        System.out.println(in.readUTF()); //вывод входящих сообщений
                    } catch (IOException e) {
                    }
                }
            }).start(); //собственно запуск потока

            while (true) {
                line = keyboard.readLine(); //в основном потоке читаю с клавы сообщение
                out.writeUTF(line); //отправляю сообщение на сервер
                out.flush(); 
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
