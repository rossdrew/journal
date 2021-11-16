import React, { Component } from 'react'

class EntryForm extends Component {
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
    }

    render() {
        return (
            <div onSubmit={this.publish} >
                <form id="journalEntryForm" >
                    <div className="mb-3">
                        <textarea className="form-control text-entry-block"
                                  rows="3"
                                  id="journalEntryInput"
                                  placeholder="Create journal entry..."
                                  name="draftBody"
                                  value={this.state.draftBody}
                                  onChange={this.handleChange}
                                  autoFocus
                                  tabIndex="0"
                                  required />
                    </div>
                </form>
            </div>

        )
    }
}

export default EntryForm