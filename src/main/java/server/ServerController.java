package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Scanner;

public class ServerController implements Runnable {

    private static final Logger log = LoggerFactory.getLogger("Server.Input");
    private InputStream consoleInput;
    private Server server;

    public ServerController(InputStream consoleInput, Server server) {
        this.consoleInput = consoleInput;
        this.server = server;
    }

    @Override
    public void run() {
        try (
                Scanner scanner = new Scanner(consoleInput)
        ) {
            while (scanner.ioException() == null) {
                System.out.println("Please enter:\n" +
                        "\"exit\" to exit the server after the last execution is done.");
                String message = scanner.next();
                if (message.contains("exit")) {
                    server.shutdownServer();
                    break;
                }
            }
        }
    }
}
