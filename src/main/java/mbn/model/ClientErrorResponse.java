package mbn.model;

public class ClientErrorResponse {

    private String errorMessage;

    public ClientErrorResponse() {
    }

    public ClientErrorResponse(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
