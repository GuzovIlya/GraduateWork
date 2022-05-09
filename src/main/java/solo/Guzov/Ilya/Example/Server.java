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
            System.out.println("Соединение принято");
            // инициируем каналы для  общения в сокете, для сервера

            // канал записи в сокет
            DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream создан");

            // канал чтения из сокета
            DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream создан");

            // начинаем диалог с подключенным клиентом в цикле, пока сокет не закрыт
            while (!client.isClosed()) {
                System.out.println("Сервер читает с канала");

                // сервер ждёт в канале чтения (inputstream) получения данных клиента
                String entry = dataInputStream.readUTF();

                // после получения данных считывает их
                System.out.println("Прочитано из сообщения клиента -> " + entry);
                // и выводит в консоль
                System.out.println("Сервер попробует написать в канал");

                // инициализация проверки условия продолжения работы с клиентом по этому сокету по кодовому слову - quit
                if (entry.equalsIgnoreCase("quit")) {
                    System.out.println("Клиент завершил соединение...");
                    dataOutputStream.writeUTF("Сервер ответил - " + entry + " - OK");
                    dataOutputStream.flush();
                    Thread.sleep(3000);
                    break;
                }

                // если условие окончания работы не верно - продолжаем работу - отправляем эхо-ответ  обратно клиенту
                dataOutputStream.writeUTF("Сервер ответил - " + entry + " - OK");
                System.out.println("Сервер написал сообщение клиенту.");

                // освобождаем буфер сетевых сообщений (по умолчанию сообщение не сразу отправляется в сеть, а сначала накапливается в специальном буфере сообщений,
                // размер которого определяется конкретными настройками в системе, а метод  - flush() отправляет сообщение не дожидаясь наполнения буфера согласно настройкам системы
                dataOutputStream.flush();
            }

            // если условие выхода - верно выключаем соединения
            System.out.println("Клиент отключился");
            System.out.println("Закрытие соединения и каналов.");


            // закрываем сначала каналы сокета !
            dataInputStream.close();
            dataOutputStream.close();

            // потом закрываем сам сокет общения на стороне сервера!
            client.close();

            // потом закрываем сокет сервера который создаёт сокеты общения
            // хотя при многопоточном применении его закрывать не нужно
            // для возможности поставить этот серверный сокет обратно в ожидание нового подключения
            System.out.println("Закрытие соединения и каналов - выполнено.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
