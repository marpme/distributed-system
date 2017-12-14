package client.request;

import client.input.WeatherResponseHandler;

import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Weather request handler
 * which extends Request handler with a body type
 * of String and Weather Response Handler as response handler
 */
public class WeatherRequestHandler extends RequestHandler<String, WeatherResponseHandler> {

    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);

    /**
     * Constructor
     * @param serverSocket server socket which should be used for sending requests to
     */
    public WeatherRequestHandler(Socket serverSocket) {
        super(serverSocket);
    }

    /**
     * Defines a output body for the current request.
     * Parses the body and puts it into the correct format for
     * our weather server
     *
     * @param output output of type String that should be sent
     * @return WeatherRequestHandler which can be used to send the request afterwards
     * @throws InvalidRequestBodyException Throws if the body has an incorrect body type.
     */
    @Override
    public WeatherRequestHandler withRequestBody(String output) throws InvalidRequestBodyException {
        try {
            if (output.matches("\\d{4}-\\d{2}-\\d{2}")) {
                Date date = dateFormatter.parse(output);
                setBody(output);
            }
        } catch (ParseException e) {
            throw new InvalidRequestBodyException("request body wasn't in the correct date format: " + DATE_FORMAT);
        }
        return this;
    }
}
