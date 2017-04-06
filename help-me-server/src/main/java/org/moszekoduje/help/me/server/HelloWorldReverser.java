package org.moszekoduje.help.me.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldReverser extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(HelloWorldReverser.class);
    private EventBus eventBus;

    @Override
    public void start() {
        eventBus = vertx.eventBus();
        eventBus.consumer("toReverse", this::reverseMessage);
    }

    private void reverseMessage(Message<String> message) {
        String reversed = new StringBuilder(message.body()).reverse().toString();
        JsonObject reversedMessage = new JsonObject().put("reversed", reversed);

        logger.info("Reversed value: {}", reversed);
        logger.info("Sending reversed message: {}", reversedMessage);

        eventBus.publish("helloworld", reversedMessage);
    }
}
