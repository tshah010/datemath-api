package com.arihant.datemathapi.model;

public class APIError {

    private ErrorResponse errorResponse;

    class ErrorResponse {
        private int id;
        private String message;
        public ErrorResponse(int id, String message) {
            this.id = id;
            this.message = message;
        }
        public void setId(int id) {
            this.id = id;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }
    }

    public APIError(int id, String message) {
        this.errorResponse = new ErrorResponse(id, message);
    }

    public ErrorResponse getErrorResponse() {
        return this.errorResponse;
    }


}
