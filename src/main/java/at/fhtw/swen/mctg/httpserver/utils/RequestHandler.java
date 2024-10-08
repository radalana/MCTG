package at.fhtw.swen.mctg.httpserver.utils;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Request;
import at.fhtw.swen.mctg.httpserver.server.Response;

import javax.annotation.processing.RoundEnvironment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//зачем имплементирует Runnable
public class RequestHandler implements Runnable {
    private Socket clientSocket; //сюда присваевается конкретный сокет с кем сервер имеет подключение в классе HttpServer
    private Router router;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader; //для чтения что прислал клиент

    public RequestHandler(Socket clientSocket, Router router) throws IOException{
        this.clientSocket = clientSocket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.router = router;
    }
    @Override
    public void run() {
        try {
            Response response;
            //read data from reuqest by RequestBuilder()
            Request request = new RequestBuilder().buildRequest(this.bufferedReader);
            //почему не создаем переменную in здесь, а прописываем как поле класса

            if (request.getPathname() == null) {
                response = new Response(
                        HttpStatus.BAD_REQUEST,
                        "{ \"message\": \"Bad Request: Missing or invalid pathname\" }"
                );
            } else {
                //если url верный отправляем на обработку
                response = this.router.resolve(request.getServiceRoute()).handleRequest(request);
            }

            printWriter.write(response.get()); //записываем http ответ
        }catch (IOException e) { //ловим исключение при записи для отправке клиенту
            System.err.println(Thread.currentThread().getName() + "Error: " + e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                    clientSocket.close();
                }
            } catch (IOException e) { //так как поток нужно закрыть в любом случае, ловим ошибке при закрытии потоков
                e.printStackTrace();
            }
        }
    }
}