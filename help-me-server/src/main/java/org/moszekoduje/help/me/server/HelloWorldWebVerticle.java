package org.moszekoduje.help.me.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldWebVerticle extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(HelloWorldWebVerticle.class);

    @Override
    public void start() {
        vertx.createHttpServer().requestHandler(createBaseRouter()::accept).listen(8080);
    }

    private Router createBaseRouter() {
        Router router = Router.router(vertx);

        router.route().handler(createCorsRequestHandler());

        router.route("/eventbus/*").handler(eventBusHandler());
        router.mountSubRouter("/api", helloWorldApiRouter());
        router.route().failureHandler(errorHandler());
        router.route().handler(staticHandler());

        return router;
    }

    private Router helloWorldApiRouter() {
        Router router = Router.router(vertx);

        router.route().handler(createCorsRequestHandler());

        router.route().handler(BodyHandler.create());

        router.route().consumes("application/json");
        router.route().produces("application/json");
        router.route().handler(staticHandler());

        router.post("/helloworld").handler(new HelloWorldReceiver());

        return router;
    }

    private CorsHandler createCorsRequestHandler() {
        return CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("Access-Control-Request-Method")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Headers")
                .allowedHeader("Content-Type");
    }

    private StaticHandler staticHandler() {
        return StaticHandler.create().setCachingEnabled(false);
    }

    private ErrorHandler errorHandler() {
        return ErrorHandler.create();
    }

    private SockJSHandler eventBusHandler() {
        BridgeOptions options = new BridgeOptions().addOutboundPermitted(new PermittedOptions().setAddressRegex(".*"));

        return SockJSHandler.create(vertx).bridge(options, event -> {
            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                logger.info("A socket was created");
            }
            event.complete(true);
        });
    }
}
