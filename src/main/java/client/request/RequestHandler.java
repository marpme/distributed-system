package client.request;

import client.input.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Request Handler for creating and sending requests
 * to our weather information server
 *
 * @param <T> Type of the Output that should be sent to the server
 * @param <K> and the type of the request class that should parse the response
 */
public abstract class RequestHandler<T, K extends ResponseHandler> {

    private final Socket serverSocket;
    private T body;

    /**
     * Constructor for our request handler
     *
     * @param serverSocket
     */
    public RequestHandler(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * Gets the body
     *
     * @return a body with the type T
     */
    public T getBody() {
        return body;
    }

    /**
     * Sets the body
     *
     * @param body the body of type T
     */
    public void setBody(T body) {
        this.body = body;
    }

    /**
     * Abstract Method
     * Appends the output to the request body of our current request
     *
     * @param output output of type T that should be sent
     * @return a request handler with the body appended
     * @throws InvalidRequestBodyException Exception will be thrown if the body is invalids
     */
    public abstract RequestHandler<T, K> withRequestBody(T output) throws InvalidRequestBodyException;

    /**
     * Default send method for writing the request to our server
     * Can be overwritten if you have to send it in a custom way
     * <p>
     * (other methods of sending)
     *
     * @param responseHandler response handler for parsing the reponse of the current request
     * @return the filled response handler which then can be used to get details about the response
     * @throws IOException throws IOException if connection isn't given or any other IOException are happening
     */
    public K send(K responseHandler) throws IOException {
        try (
                PrintWriter serverWriter = new PrintWriter(serverSocket.getOutputStream());
                BufferedReader serverReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))
        ) {
            serverWriter.println(body);
            serverWriter.flush();

            responseHandler.setResponseBody(serverReader.readLine());
            return responseHandler;
        }
    }
}
