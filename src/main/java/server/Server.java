package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jan Kulose - s0557320 on 02.11.17.
 */
public class Server {

    private final static int PORT = 1234;
    private static final Logger log = LoggerFactory.getLogger("Main");
    private static ExecutorService executor = Executors.newCachedThreadPool();
    private boolean serverIsRunning = true;
    private ServerSocket socket;

    /**
     * Server CTOR
     */
    private Server() {
        try (
                ServerSocket socket = new ServerSocket(PORT)
        ) {
            this.socket = socket;
            executor.execute(new ServerController(System.in, this));
            log.info("Server opened socket on port: {}.", PORT);
            while (this.shouldServerRunning()) {
                log.info("Accepting connections to our web socket port {}", PORT);
                executor.execute(new TemperatureHandler(socket.accept()));
            }

        } catch (SocketException e) {
            log.info("Shutting down server and waiting to finish last executions!");
            executor.shutdown();
            try {
                executor.awaitTermination(15L, TimeUnit.SECONDS);
            } catch (InterruptedException ie) {
                log.info("Some executions took too long so we interrupted them manually!");
            }
        } catch (IOException e) {
            log.error("An error occurred while starting the server!", e);
        }

        log.info("Shutdown Server.");
    }

    /**
     * Entry point
     *
     * @param args any arguments are being ignored.
     */
    public static void main(String[] args) {
        new Server();
    }

    /**
     * Checks if the server should still be online
     * or not and returns the value.
     *
     * @return boolean which decides if the server should stop or not.
     */
    private boolean shouldServerRunning() {
        return this.isServerIsRunning();
    }

    /**
     * Checks if the server is still running
     *
     * @return boolean - true = running, false = shutdown
     */
    public boolean isServerIsRunning() {
        return serverIsRunning;
    }

    /**
     * Set if the server should still run or stop running
     */
    public void shutdownServer() {
        this.serverIsRunning = false;
        try {
            this.socket.close();
        } catch (IOException e) {
            log.error("Couldn't close the server socket!", e);
        }
    }

}

