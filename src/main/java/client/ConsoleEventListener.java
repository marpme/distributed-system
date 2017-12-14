package client;

import java.util.EventListener;

/**
 * Created by Jan Kulose - s0557320 on 02.11.17.
 */
public interface ConsoleEventListener extends EventListener {
    void onConsoleEvent(ConsoleEvent consoleEvent);
}
