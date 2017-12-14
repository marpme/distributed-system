package client.input;

import client.ResponseStatus;

import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Weather data storing class
 */
public class WeatherData {

    /**
     * Repsonse status (error, ok, unknown)
     */
    private final ResponseStatus status;

    /**
     * Message that we might receive on error or unknown
     */
    private final String errorMessage;

    /**
     * All temperatures for one day
     */
    private List<Double> temperatures = null;

    /**
     * Double statistics including avg, max, min etc
     */
    private DoubleSummaryStatistics summaryStatistics = null;

    public WeatherData(ResponseStatus status, String errorMessage, List<Double> temperatures, DoubleSummaryStatistics summaryStatistics) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.temperatures = temperatures;
        this.summaryStatistics = summaryStatistics;
    }

    public WeatherData(ResponseStatus status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<Double> getTemperatures() {
        return temperatures;
    }

    public DoubleSummaryStatistics getSummaryStatistics() {
        return summaryStatistics;
    }
}
