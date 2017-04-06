import React, {Component} from "react";

class HelloWorld extends Component {
    constructor() {
        super();

        this.state = {
            toServer: '',
        };

        this.handleChange = this.handleChange.bind(this);
    }

    render() {
        return (
            <div className="HelloWorld">
                <form>
                    <label>
                        Message to server:
                        <input
                            type="text"
                            id="messageToServer"
                            value={this.state.toServer}
                            onChange={this.handleChange}/>
                    </label>
                </form>
            </div>
        );
    }

    handleChange(event) {
        this.setState({
            fromServer: this.state.fromServer,
            toServer: event.target.value,
        });

        fetch('http://localhost:8080/api/helloworld', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                value: event.target.value,
            })
        }).then(function (response) {
            if (response.status >= 200 && response.status < 300) {
                return response
            } else {
                const error = new Error(response.statusText);
                error.response = response;
                throw error
            }
        })
    }
}

export default HelloWorld;