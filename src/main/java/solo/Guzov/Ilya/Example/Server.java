package solo.Guzov.Ilya.Example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        //  стартуем сервер на порту 3345
        try (ServerSocket serverSocket = new ServerSocket(3345)) {
            // становимся в ожидание подключения к сокету под именем - "client" на серверной стороне
            Socket client = serverSocket.accept();
            // после хэндшейкинга сервер ассоциирует подключающегося клиента с этим сокетом-соединением
            System.out.println("Connection accepted");
            // инициируем каналы для  общения в сокете, для сервера

            // канал записи в сокет
            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream created");

            // канал чтения из сокета
            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");

            // начинаем диалог с подключенным клиентом в цикле, пока сокет не закрыт
            while (!client.isClosed()) {
                System.out.println("Server reading from channel");

                // сервер ждёт в канале чтения (inputstream) получения данных клиента
                String entry = dataInputStream.readUTF();

                // после получения данных считывает их
                System.out.println("READ from client message - " + entry);
                // и выводит в консоль
                System.out.println("Server try writing to channel");

                // инициализация проверки условия продолжения работы с клиентом по этому сокету по кодовому слову - quit
                if (entry.equalsIgnoreCase("quit")) {
                    System.out.println("Client initialize connections suicide ...");
                    dataOutputStream.writeUTF("Server reply - " + entry + " - OK");
                    dataOutputStream.flush();
                    Thread.sleep(3000);
                    break;
                }

                // если условие окончания работы не верно - продолжаем работу - отправляем эхо-ответ  обратно клиенту
                dataOutputStream.writeUTF("Server reply - " + entry + " - OK");
                System.out.println("Server Wrote message to client.");

                // освобождаем буфер сетевых сообщений (по умолчанию сообщение не сразу отправляется в сеть, а сначала накапливается в специальном буфере сообщений,
                // размер которого определяется конкретными настройками в системе, а метод  - flush() отправляет сообщение не дожидаясь наполнения буфера согласно настройкам системы
                dataOutputStream.flush();
            }

            // если условие выхода - верно выключаем соединения
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");


            // закрываем сначала каналы сокета !
            dataInputStream.close();
            dataOutputStream.close();

            // потом закрываем сам сокет общения на стороне сервера!
            client.close();

            // потом закрываем сокет сервера который создаёт сокеты общения
            // хотя при многопоточном применении его закрывать не нужно
            // для возможности поставить этот серверный сокет обратно в ожидание нового подключения
            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
