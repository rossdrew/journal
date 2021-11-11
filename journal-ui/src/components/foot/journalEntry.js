import React, { Component } from 'react'

class JournalEntry extends Component {
    render() {
        return (
            <div className="card w-75 p-0" key={this.entryCardKeyPrefix+this.props.index}>
                <div className="card-body" key={this.entryCardKeyPrefix+"body"+this.props.index}>
                    <h5 className="card-title" key={this.entryCardKeyPrefix+"title"+this.props.index}>entry {this.props.index}</h5>
                    <h6 className="card-subtitle mb-2 text-muted" key={this.entryCardKeyPrefix+"subtitle"+this.props.index}><i>{new Date(this.props.entry.creation).toLocaleString()}</i></h6>
                    <p className="card-text" key={this.entryCardKeyPrefix+"text"+this.props.index}>{this.props.entry.body}</p>
                </div>
            </div>
        )
    }
}

export default JournalEntry