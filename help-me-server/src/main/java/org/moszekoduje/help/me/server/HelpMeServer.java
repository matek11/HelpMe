package org.moszekoduje.help.me.server;

import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelpMeServer {

    private static Logger logger = LoggerFactory.getLogger(HelpMeServer.class);

    public static void main(String[] args) {
        logger.info("Starting HelpMeServer... ");

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HelloWorldWebVerticle());
        vertx.deployVerticle(new HelloWorldReverser());

        logger.info("... HelpMeServer started! ");
    }

}
