package Server;

import java.net.*;
import java.io.*;
public class Server {

    public static void main(String[] ar) {
        int port = 8189;
        try {
            ServerSocket ss = new ServerSocket(port); //запускаю порт сервера
            System.out.println("Ожидание подключения...");


            Socket socket = ss.accept(); //цепляю входящего клиента на сокет
            System.out.println("Связь установлена!");
            System.out.println();

            DataInputStream in = new DataInputStream(socket.getInputStream()); //поток сюда
            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); //поток отсюда

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in)); //цепляю клавиатуру на поток
            String line;

            new Thread(() -> { //отдельный поток на входящий поток
                while (true) {
                    try {
                        System.out.println(in.readUTF()); //читаю и печатаю поток сюда
                    } catch (IOException e) {
                    }
                }
            }).start();//собственно запуск потока

            while (true) {
                line = keyboard.readLine(); //считываю из буфера, что введено с клавы
                out.writeUTF(line);//отправляю сообщение клиенту
                out.flush();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
