import React, { Component } from 'react';
import './App.css';
import MessagesList from './MessagesList';
import MessageEdit from './MessageEdit';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={MessagesList}/>
		  <Route path='/messages/:id' component={MessageEdit}/>
        </Switch>
      </Router>
    )
  }
}

export default App;