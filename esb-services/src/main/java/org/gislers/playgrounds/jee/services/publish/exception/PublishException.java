package org.gislers.playgrounds.jee.services.publish.exception;

/**
 * Created by:   jgisle
 * Created date: 10/12/15
 */
public class PublishException extends RuntimeException {

    public PublishException() {
        super();
    }

    public PublishException(String message) {
        super(message);
    }

    public PublishException(String message, Throwable cause) {
        super(message, cause);
    }

    public PublishException(Throwable cause) {
        super(cause);
    }
}
