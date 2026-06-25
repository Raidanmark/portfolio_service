package portfolioservice.server.http;

import io.netty.handler.codec.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RouteKey {

    private final HttpMethod method;
    private final String path;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof RouteKey other)) {
            return false;
        }

        return method.equals(other.method)
                && path.equals(other.path);
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + path.hashCode();
        return result;
    }
}
