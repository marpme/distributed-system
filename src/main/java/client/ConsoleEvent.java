package client;

import java.io.InputStream;
import java.util.EventObject;


/**
 * Stores console event data for the Event
 */
public class ConsoleEvent extends EventObject {
    private static final long serialVersionUID = -2348723947398111324L;
    private final String message;

    /**
     * Constructor
     * @param source Input stream from source (Console)
     * @param message Message from the console
     */
    public ConsoleEvent(InputStream source, String message) {
        super(source);
        this.message = message;
    }

    /**
     * Gets the message from the current event
     * @return a String with the message from the console
     */
    public String getMessage() {
        return message;
    }
}
