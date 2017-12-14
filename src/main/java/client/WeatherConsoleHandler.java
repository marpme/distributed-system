package client;

import client.input.WeatherData;

import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * Weather console handler
 * It handles the preparation of showing the parsed weather information
 * inside of the console in form of a simple table.
 */
public class WeatherConsoleHandler extends ConsoleHandler {

    /**
     * Constructor for the console input stream
     *
     * @param consoleInput the reference to the input stream
     */
    WeatherConsoleHandler(InputStream consoleInput) {
        super(consoleInput);
    }

    /**
     * Prints the current weather information to the console
     *
     * @param weatherInformation our parsed weather information
     */
    public void printCurrentWeatherData(WeatherData weatherInformation) {
        if (ResponseStatus.OK == weatherInformation.getStatus()) {

            if (weatherInformation.getTemperatures().size() != 24) {
                System.out.println("For your inquired date, we haven't got any/enough data.\n" +
                        "Either it's in the future or we haven't got any past data for the given date.\n");
                return;
            }

            String leftAlignFormat = "| %02d:00 | %-11s |%n";
            String statsFormat = "| %-5s | %-11s |%n";

            System.out.format("+-------+-------------+%n");
            System.out.format("| Time  | Temperature |%n");
            System.out.format("+-------+-------------+%n");

            for (int i = 0; i < weatherInformation.getTemperatures().size(); i++) {
                System.out.format(leftAlignFormat, i, weatherInformation.getTemperatures().get(i) + " °C");
            }
            System.out.format("+-------+-------------+%n");

            DecimalFormat df = new DecimalFormat("0.0 °C");
            System.out.format(statsFormat, "AVG", df.format(weatherInformation.getSummaryStatistics().getAverage()));
            System.out.format(statsFormat, "MIN", df.format(weatherInformation.getSummaryStatistics().getMin()));
            System.out.format(statsFormat, "MAX", df.format(weatherInformation.getSummaryStatistics().getMax()));

            System.out.format("+-------+-------------+%n");
        } else {
            System.out.println("Error: " + weatherInformation.getErrorMessage());
        }
    }
}
