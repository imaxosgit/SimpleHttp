/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.*;
import java.io.*;
public class Server {
   public static void main(String[] ar)    {
     int port = 6666; // случайный порт (может быть любое число от 1025 до 65535)
       try {
         ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
         System.out.println("Waiting for a client...");

         Socket socket = ss.accept(); // заставляем сервер ждать подключений
         System.out.println();

 // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту. 
         InputStream sin = socket.getInputStream();
         OutputStream sout = socket.getOutputStream();

 // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
         DataInputStream in = new DataInputStream(sin);
         DataOutputStream out = new DataOutputStream(sout);

         String line;
         while(true) {
           line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
           if(line.equals("GET")) {
                String l;
                String sysUserName=System.getProperty("user.name");
                Process p = Runtime.getRuntime().exec("ps -u "+sysUserName+"");
                BufferedReader input =
                                new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while ((l = input.readLine()) != null) {
                         out.writeUTF(l);

    }
    input.close();
           }
           else
               System.out.println("Unknown command");
           out.flush(); // заставляем поток закончить передачу данных.
           System.out.println("Waiting for the next command...");
           System.out.println();
         }
      } catch(Exception x) { x.printStackTrace(); }
   }
}
