package ru.vinogradov.mst.market.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class NotForUsersFilter extends AbstractGatewayFilterFactory<NotForUsersFilter.Config> {
    private final String pathListProduct = "/market-core/api/v1/products/listProducts";

    public NotForUsersFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            System.out.println(request.getHeaders());
            System.out.println(request.getURI().getPath());
            if (request.getHeaders().containsKey("role")) {
                List<String> listRole = request.getHeaders().get("role");
                assert listRole != null;
                for (String role : listRole) {
                    if (role.equals("[ROLE_USER]") && request.getURI().getPath().contains(pathListProduct)) {
                        return this.onError(exchange, "Not for users", HttpStatus.UNAUTHORIZED);
                    }
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
