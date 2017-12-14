package client.input;

/**
 * Abstract Response handler which will be used by
 * a request handler to parse the received response
 * @param <T>
 */
public abstract class ResponseHandler<T> {

    /**
     * Response body as String
     */
    private String responseBody;

    /**
     * Parses the output of the given response body
     * @return returns the given class as type of T
     */
    public abstract T getParsedOutput();

    /**
     * Gets the pure repsonse body
     * @return
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * Sets the response body
     * @param responseBody response body that should be set
     */
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
