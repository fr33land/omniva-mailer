import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class MessagesList extends Component {

  constructor(props) {
    super(props);
    this.state = {messages: [], isLoading: true};
	this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});
    fetch('/email/get/all')
      .then(response => response.json())
      .then(data => this.setState({messages: data, isLoading: false}));
  }
  
  async remove(messageId) {
    await fetch('/email/delete/' + messageId, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
        let updatedMessages = [...this.state.messages].filter(i => i.messageId !== messageId);
        this.setState({messages: updatedMessages});
      });
  }

  render() {
    const {messages, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const messagesList = messages.map(message => {
      return <tr key={message.messageId}>
	    <td>{message.messageId}</td>
        <td style={{whiteSpace: 'nowrap'}}>{message.messageReceiver}</td>
        <td>{message.messageSubject}</td>
        <td>{message.message}</td>
		<td>{message.delivered}</td>
		<td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/messages/" + message.messageId}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(message.messageId)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/messages/new">Add message</Button>
          </div>
          <h3>Email messages</h3>
          <Table className="mt-4" width="80px">
            <thead>
            <tr>
              <th width="3%">Id</th>
              <th width="10%">Message receiver</th>
              <th width="10%">Subject</th>
              <th width="40%">Message</th>
			  <th width="10%">Delivered</th>
			  <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {messagesList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default MessagesList;