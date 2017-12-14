package client;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to register all events on the console
 * and listen for possible inputs
 */
public class ConsoleHandler {
    private List<ConsoleEventListener> listenerList = null;
    private InputStream consoleInput = null;

    ConsoleHandler(InputStream consoleInput) {
        listenerList = new ArrayList<>();
        this.consoleInput = consoleInput;
    }

    /**
     * Adds a custom console event listener
     * @param listener reference of the listener
     */
    void add(ConsoleEventListener listener) {
        listenerList.add(listener);
    }

    /**
     * Removes the listener by search for the reference
     * @param listener reference of the listener
     */
    public void remove(ConsoleEventListener listener) {
        listenerList.remove(listener);
    }

    void start() {
        try (Scanner scanner = new Scanner(consoleInput)) {
            while (scanner.ioException() == null) {
                System.out.println("Please enter:\n" +
                        "\"YYYY-MM-DD\" to receive the temperature readings for that date\n" +
                        "\"exit\" to exit the application");
                String message = scanner.next();
                notifyEventListeners(new ConsoleEvent(consoleInput, message));
            }
            System.out.println(scanner.ioException());
        }
    }

    /**
     * If events get triggered by the console, then go though all
     * listeners and notify them.
     * @param event reference to an event, that was triggered by the console
     */
    private void notifyEventListeners(ConsoleEvent event) {
        for (ConsoleEventListener listener : listenerList) {
            listener.onConsoleEvent(event);
        }
    }
}
