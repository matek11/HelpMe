import React, { Component } from 'react';
import HelloWorld from './HelloWorld';
import HelloWorldResponse from './HelloWorldResponse';
import './App.css';

class App extends Component {
  render() {
    return (
      <div className="App">
        <HelloWorld/>
        <HelloWorldResponse/>
      </div>
    );
  }
}

export default App;
