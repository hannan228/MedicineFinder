package jav.app.medicinefinder.Model;

public class Message {
    private String message;
    private String sender;
    private String sendTo;
    private String date;

    public Message() {
    }

    public Message(String message, String sender, String sendTo, String date) {
        this.message = message;
        this.sender = sender;
        this.sendTo = sendTo;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getSendTo() {
        return sendTo;
    }

    public String getDate() {
        return date;
    }
}
