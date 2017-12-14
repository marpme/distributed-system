package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

public class TemperatureHandler implements Runnable {

    private static final Logger log = LoggerFactory.getLogger("Handler");
    private final Socket clientSocket;
    private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    private TemperatureHandler() {
        clientSocket = new Socket();
    }

    TemperatureHandler(Socket clientSocket) {
        log.info("New client connected! Further Information:");
        log.info("Client IP: {}", clientSocket.getInetAddress().getHostAddress());
        log.info("Client Port: {}", clientSocket.getPort());
        this.clientSocket = clientSocket;
    }

    private Socket getClientSocket() {
        return clientSocket;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        log.info("Thread #{} is serving the weather data.", Thread.currentThread().getId());
        try (
                InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader clientReader = new BufferedReader(inputStreamReader);
        ) {
            String clientRequest = clientReader.readLine();
            log.info("The actual date sent was: {}", clientRequest);

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalMonitorStateException e) {
                e.printStackTrace();
                return;
            }

            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream())) {
                // Date parsing
                Date dateOfTemp;
                try {
                    dateOfTemp = SDF.parse(clientRequest);
                } catch (ParseException pe) {
                    pe.printStackTrace();
                    respond("2; Parse exception for date. Must be in format: yyyy-MM-dd", out);
                    return;
                }

                // Temp history
                Optional<TemperatureHistory> history = TemperatureReader.getInstance().receiveTemperatureForDate(dateOfTemp);
                String response = history
                        .map(data -> "0;" + data.getWeatherData().stream().collect(Collectors.joining(";")))
                        .orElse("1; No weather data found for your date.");

                respond(response, out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void respond(String response, PrintWriter out) {
        out.println(response);
        out.flush();
        log.info("The server's response was (shortened):");
        log.info(response.length() > 20 ?
                response.substring(0, 20) + "(...)" :
                response.substring(0, response.length() - 1) + "(...)");
    }
}
