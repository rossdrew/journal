import React, { Component } from 'react'

class EntryForm extends Component {
    constructor() {
        super();
        this.state = {
            jbody: ""
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
        fetch('http://localhost:8080/entries/append', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                body: this.state.jbody,
            })
        })
    }

    render() {
        return <div>
            <input
                type="text"
                name="jbody"
                placeholder="Entry here..."
                value={ this.state.jbody }
                onChange={ this.handleChange }
            />

            <button value="Send" onClick={ this.publish }>Publish</button>
        </div>
    }
}

export default EntryForm