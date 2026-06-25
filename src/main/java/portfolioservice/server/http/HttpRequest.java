package portfolioservice.server.http;

import io.netty.handler.codec.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class HttpRequest {

    private final PortfolioHttpMethod method;
    private final String path;
    private final String body;
    private final Map<String,String> headers;
    private final Map<String,String> queryParams;
    private  Map<String,String> pathVariables;

    public String pathVariable(String name) {
        return pathVariables.get(name);
    }
}
