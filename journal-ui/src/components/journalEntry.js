import React, { Component } from 'react'

class JournalEntry extends Component {
    render() {
        return (
            <div className="card w-100 p-0" key={this.props.keyPrefix+"content"}>
                <div className="card-body">
                    <sup key={this.props.keyPrefix+"date"}
                         className="entry-date">
                        {new Date(this.props.entry.creation).toLocaleString()}
                    </sup>

                    <p key={this.props.keyPrefix+"body"}
                       className="entry-body">
                        {this.props.entry.body}
                    </p>

                    <sub key={this.props.keyPrefix+"meta"}
                         className="entry-meta">
                        Index: {this.props.index}
                    </sub>
                </div>
            </div>
        )
    }
}


export default JournalEntry