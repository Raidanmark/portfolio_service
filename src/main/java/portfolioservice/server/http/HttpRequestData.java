package portfolioservice.server.http;

import io.netty.handler.codec.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class HttpRequestData {

    private final HttpMethod method;
    private final String path;
    private final String body;
    private final Map<String,String> pathVariables;

    public String pathVariable(String name) {
        return pathVariables.get(name);
    }
}
