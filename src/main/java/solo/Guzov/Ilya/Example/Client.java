package solo.Guzov.Ilya.Example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client{

    private static String respServer = "Ответа от сервера еще не было";
    private static String clientCommand = "";

    public static void setClientCommand(String command){
        clientCommand = command;
    }

    public static String getClientCommand(){
        return clientCommand;
    }

    public static String getRespServer(){
        return respServer;
    }

    public static void setRespServer(String resp){
        respServer = resp;
    }

    public static void main(String[] args) throws InterruptedException{
        Window window = new Window();
        Thread myThread = new Thread(window);
        myThread.start();


        // запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента
        try (Socket socket = new Socket("localhost", 3345);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream()))
        {
            System.out.println("Клиент подключен к сокету");
            System.out.println();
            System.out.println("Канал записи клиента = dos и канал чтения = dis инициализированы.");

            // проверяем живой ли канал и работаем если живой
            while (!socket.isOutputShutdown()){
                Thread.sleep(100);

                // ждём консоли клиента на предмет появления в ней данных
                if(!getClientCommand().equalsIgnoreCase("")){
                    // данные появились - работаем
                    System.out.println("Клиент начинает писать в канал...");
                    Thread.sleep(1000);
//                    clientCommand = bufferedReader.readLine();

                    // пишем данные с консоли в канал сокета для сервера
                    dataOutputStream.writeUTF(getClientCommand());
                    dataOutputStream.flush();
                    System.out.println("Клиент отправил сообщение '" + getClientCommand() + "' серверу.");
                    Thread.sleep(1000);

                    // ждём чтобы сервер успел прочесть сообщение из сокета и ответить
                    // проверяем условие выхода из соединения
                    if(getClientCommand().equalsIgnoreCase("quit")){
                        System.out.println("Клиент уничтожил соединение");
                        Thread.sleep(2000);

                        // смотрим что нам ответил сервер на последок перед закрытием ресурсов
//                        if(dataInputStream.read() > -1){
                            System.out.println("Чтение...");
                            setRespServer(dataInputStream.readUTF());
                            System.out.println(respServer);
//                        }

                        // после предварительных приготовлений выходим из цикла записи чтения
                        break;
                    }
                // если условие разъединения не достигнуто продолжаем работу
                    System.out.println("Клиент отправил сообщение и начал ждать данных с сервера...");
                    Thread.sleep(1000);

                    // проверяем, что нам ответит сервер на сообщение(за предоставленное ему время в паузе он должен был успеть ответить)
//                    if(dataInputStream.read() > -1){
                        // если успел забираем ответ из канала сервера в сокете и сохраняем её в ois переменную,  печатаем на свою клиентскую консоль
                        System.out.println("Чтение...");
                        setRespServer(dataInputStream.readUTF());
                        System.out.println(respServer);
//                    }
                    setClientCommand("");
                }
            }
            // на выходе из цикла общения закрываем свои ресурсы
            System.out.println("Закрытие соединений и каналов на стороне клиента - ВЫПОЛНЕНО.");
        }   catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
