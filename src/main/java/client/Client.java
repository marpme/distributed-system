package client;

import client.input.WeatherData;
import client.input.WeatherResponseHandler;
import client.request.InvalidRequestBodyException;
import client.request.WeatherRequestHandler;

import java.io.IOException;
import java.net.Socket;

/**
 * Client for gathering weather inforamtion form a
 * specific server
 * <p>
 * Please change the setting details inside of <FILE></FILE>
 */
public class Client {
    static String serverAddress = "localhost";
    static int serverPort = 1234;

    public static void main(String[] args) {
        WeatherConsoleHandler handler = new WeatherConsoleHandler(System.in);

        handler.add(event -> {
            if (event.getMessage().matches("\\d{4}-\\d{2}-\\d{2}")) {
                try (
                        Socket serverSocket = new Socket(serverAddress, serverPort);
                ) {
                    WeatherRequestHandler requestHandler = new WeatherRequestHandler(serverSocket);
                    WeatherResponseHandler responseHandler = new WeatherResponseHandler();

                    WeatherData weatherInformation = requestHandler
                            .withRequestBody(event.getMessage())
                            .send(responseHandler)
                            .getParsedOutput();
                    handler.printCurrentWeatherData(weatherInformation);
                } catch (InvalidRequestBodyException e) {
                    System.out.println("We couldn't proceed with the given Date. Please check your date again and make sure it's in the correct format.");
                } catch (IOException e) {
                    System.out.println("The server had some problems processing our request. Please try again later.");
                }
            }
        });

        handler.add((ConsoleEvent event) -> {
            if (event.getMessage().equals("exit")) {
                System.out.println("Exiting application...");
                System.exit(0);
            }
        });

        // blocking
        handler.start();


    }

}
