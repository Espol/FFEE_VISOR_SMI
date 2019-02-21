package com.csti.ce.util;

public class Response {

    private boolean success;
    private Object data;
    private String message;

    public Response(String message) {
        // TODO Auto-generated constructor stub
        this.success = false;
        this.message = message;
        this.data = "";
    }

    public Response() {
        this.success = false;
        this.message = "";
        this.data = "";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void messageDELETE() {
        this.setMessage("Se eliminó correctamente...");
        this.setSuccess(true);
    }

    public void messageUPDATE() {
        this.setMessage("Se actualizó correctamente...");
        this.setSuccess(true);
    }

    public void messageINSERT() {
        this.setMessage("Se guardo correctamente...");
        this.setSuccess(true);
    }

    public void messageERROR() {
        this.setMessage("Ha ocurrido un error...");
        this.setSuccess(false);
    }

    @Override
    public String toString() {
        return "{\"success\":" + this.isSuccess() + ",\"message\":{\"reason\": \"" + this.getMessage() + "\"}}";

    }
}
