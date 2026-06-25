package portfolioservice.server.http;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HttpResponse {

    private final HttpResponseStatus status;
    private final String body;

    public static HttpResponse ok(String body) {
        return new HttpResponse(HttpResponseStatus.OK, body);
    }

    public static HttpResponse error(HttpResponseStatus status, String body) {
        return new HttpResponse(status, body);
    }
}
