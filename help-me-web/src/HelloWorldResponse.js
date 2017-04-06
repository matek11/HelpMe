import React, {Component} from "react";
import EventBus from "vertx3-eventbus-client";

class HelloWorldResponse extends Component {
    constructor() {
        super();

        this.state = {
            fromServer: null,
        };

        this.registerEventBusHandler.bind(this);
        this.registerEventBusHandler(this)
    }

    render() {
        const fromServer = this.state.fromServer;

        return (
            <h2>HelloWorld! Server says: {fromServer}</h2>
        );
    }

    registerEventBusHandler(helloWorldResponse) {
        const eventBus = new EventBus('http://localhost:8080/eventbus');

        eventBus.onopen = function () {
            eventBus.registerHandler('helloworld', function (error, message) {
                console.log(message.body);
                helloWorldResponse.setState({fromServer: message.body.reversed});
            });
        }
    }
}

export default HelloWorldResponse;