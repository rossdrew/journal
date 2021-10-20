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
        window.location.reload(); //This is less than ideal, it loses focus
    }

    render() {
        return <div>
            <form className="entryForm" onSubmit={this.publish}>
                <input
                    type="text"
                    name="draftBody"
                    placeholder="Entry here..."
                    value={ this.state.draftBody }
                    onChange={ this.handleChange }
                />

                <button type="submit" value="Send">Publish</button>
            </form>
        </div>
    }
}

export default EntryCreationForm