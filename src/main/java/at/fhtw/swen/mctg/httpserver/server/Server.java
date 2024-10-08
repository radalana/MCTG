package at.fhtw.swen.mctg.httpserver.server;

import at.fhtw.swen.mctg.httpserver.utils.RequestHandler;
import at.fhtw.swen.mctg.httpserver.utils.Router;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//класс ждет кто подключится, для каждого подключения создает поток и RequestHander уже отвественен за чтение /написание в каждый отедльный клиент-поток-сокет
public class Server {
    private int port;
    private Router router; //соединяет путь с соотвествущем сервисом

    public Server(int port, Router router) {
        this.port = port;
        this.router = router;
    }

    public void start() throws IOException {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        System.out.println("Start http-server...");
        System.out.println("http-server running at: http://localhost:" + this.port);

        //создаем сокет сервера который как дверь
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                //ждет подключения сервера
                final Socket clientConnection = serverSocket.accept();
                final RequestHandler socketHandler = new RequestHandler(clientConnection, this.router);
                executorService.submit(socketHandler);
            }
        }
    }
}
