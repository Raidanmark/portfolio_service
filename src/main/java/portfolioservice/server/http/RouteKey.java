package portfolioservice.server.http;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class RouteKey {

    private final PortfolioHttpMethod method;
    private final String path;

}
