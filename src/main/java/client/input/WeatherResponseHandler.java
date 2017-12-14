package client.input;

import client.ResponseStatus;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Weather response handler, extends abstract response handler
 * which parses response into the given WeatherData class
 */
public class WeatherResponseHandler extends ResponseHandler<WeatherData> {

    /**
     * Gets the parsed output from our response that we received.
     * This doesn't do the type checks, should be done in the specific class
     * @return a reference to the parsed weather data
     */
    @Override
    public WeatherData getParsedOutput() {
        List<String> response = Arrays.stream(getResponseBody().split(";")).collect(Collectors.toList());

        int statusCode = Integer.parseInt(response.remove(0));
        ResponseStatus status = ResponseStatus.fromInt(statusCode);

        String errorMessage = null;
        
        if (status == ResponseStatus.ERROR) {
            errorMessage = response.get(0);
            return new WeatherData(status, errorMessage);
        } else if (status == ResponseStatus.UNKNOWN) {
            errorMessage = "Our client doesn't support this type of error.";
            return new WeatherData(status, errorMessage);
        }


        List<Double> temperatures = response.stream().mapToDouble(Double::parseDouble).boxed().collect(Collectors.toList());
        DoubleSummaryStatistics statistics = response.stream().mapToDouble(Double::parseDouble).summaryStatistics();

        return new WeatherData(status, errorMessage, temperatures, statistics);
    }
}
