import React, { Component } from 'react'

class EntryCreationForm extends Component {
    constructor() {
        super();
        this.state = {
            draftBody: ""
        };

        this.publish = this.publish.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange({ target }) {
        this.setState({
            [target.name]: target.value
        });
    }

    publish() {
        //TODO This modifies the browser URL, is there a smoother way to do it?
        fetch('http://localhost:8080/entries/append', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                body: this.state.draftBody,
            })
        })
       this.props.requestRefresh();
    }

    render() {
        return <div>
            <form onSubmit={this.publish}>
                <textarea id="author"
                          type="text"
                          name="draftBody"
                          value={ this.state.draftBody }
                          onChange={ this.handleChange }
                          autoFocus
                          tabIndex="0" />
                <button type="submit" value="Send" tabIndex="1">Publish</button>
            </form>
        </div>
    }
}

export default EntryCreationForm