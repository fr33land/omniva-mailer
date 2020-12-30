import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class MessageEdit extends Component {

  emptyItem = {
    messageReceiver: '',
    messageSubject: '',
    message: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
      errors: []
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const msg = await (await fetch(`/email/get/${this.props.match.params.id}`)).json();
      this.setState({item: msg});
    }
  }
  
  hasError(key) {
    return this.state.errors.indexOf(key) !== -1;
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {

    event.preventDefault();
    const {item} = this.state;
	
	var errors = [];

	const expression = /\S+@\S+/;
    var validEmail = expression.test(String(item.messageReceiver).toLowerCase());

    if (!validEmail) {
      errors.push("messageReceiver");
    }
	
	if (item.messageSubject === "") {
      errors.push("messageSubject");
    }
	
	if (item.message === "") {
      errors.push("message");
    }

	this.setState({
      errors: errors
    });

    if (errors.length > 0) {
      return false;
    } else {
      await fetch((item.messageId ? '/email/update/' + item.messageId : '/email/create'), {
		  method: (item.messageId) ? 'PUT' : 'POST',
		  headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		  },
		  body: JSON.stringify(item),
		});
	  this.props.history.push('/');
    }
	
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.messageId ? 'Edit message' : 'Add message'}</h2>;

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
		  <FormGroup>
            <Label for="messageId">Id</Label>
            <Input type="text" name="messageId" id="messageId" value={item.messageId || ''} disabled/>
          </FormGroup>
          <FormGroup>
            <Label for="messageReceiver">Message receiver</Label>
            <Input type="text" name="messageReceiver" id="messageReceiver" value={item.messageReceiver || ''} onChange={this.handleChange} className={ this.hasError("messageReceiver") ? "form-control is-invalid" : "form-control" } />
			<div className={this.hasError("messageReceiver") ? "inline-errormsg" : "hidden"} > Please enter valid email </div>
          </FormGroup>
          <FormGroup>
            <Label for="messageSubject">Message subject</Label>
            <Input type="text" name="messageSubject" id="messageSubject" value={item.messageSubject || ''} onChange={this.handleChange} className={ this.hasError("messageSubject") ? "form-control is-invalid" : "form-control" }  />
			<div className={this.hasError("messageSubject") ? "inline-errormsg" : "hidden"} > Please enter message subject </div>
          </FormGroup>
          <FormGroup>
            <Label for="message">Message</Label>
            <Input type="textarea" name="message" id="message" value={item.message || ''} onChange={this.handleChange} className={ this.hasError("message") ? "form-control is-invalid" : "form-control" }  />
			<div className={this.hasError("message") ? "inline-errormsg" : "hidden"} > Please enter message content </div>
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(MessageEdit);