package portfolioservice.server.http;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class AppHttpResponse {

    private final HttpResponseStatus status;
    private final String body;

    public static AppHttpResponse ok(String body) {
        return new AppHttpResponse(HttpResponseStatus.OK, body);
    }

    public static AppHttpResponse error(HttpResponseStatus status, String body) {
        return new AppHttpResponse(status, body);
    }
}
