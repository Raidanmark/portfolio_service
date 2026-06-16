package portfolioservice.protocol;

public class ResponseWriter {

    public String ok(String body) {
        return "OK " + body + "\n";
    }

    public String error(String code, String message) {
        return "ERROR code=" + code + "message=" + escape(message) + "\"\n";
    }

    private String escape(String message) {
        return message.replace("\"", "\\\"");
    }
}
