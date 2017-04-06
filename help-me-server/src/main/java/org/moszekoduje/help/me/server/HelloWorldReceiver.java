package org.moszekoduje.help.me.server;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.DecodeException;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldReceiver implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(HelloWorldReceiver.class);

    @Override
    public void handle(RoutingContext event) {
        EventBus eventBus = event.vertx().eventBus();
        try {
            String value = event.getBodyAsJson().getString("value");

            logger.info("Received following message: {}", event.getBodyAsString());
            logger.info("Received following value: {}", value);

            if (value != null && !"".equals(value)) {
                eventBus.publish("toReverse", value);
            }

            event.response().setStatusCode(200).end();
        } catch (DecodeException de) {
            event.response().setStatusCode(400).end();
        }
    }

}
