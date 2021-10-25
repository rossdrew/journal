import React, { Component } from 'react'

class JournalEntries extends Component {
    state = {
        entries: [],
        lastUpdated: "unknown"
    }

    entryCardKeyPrefix = "entry-card-"

    componentDidMount() {
        this.refresh()
    }

    refresh() {
        fetch('http://localhost:8080/entries')
            .then(res => res.json())
            .then((data) => {
                this.setState({
                    entries: data,
                    lastUpdated: new Date()
                })
            }).catch(console.log);
    }

    render() {
        //Ordered by date, latest at top - why doesn't this work in the refresh method?
        this.state.entries.sort((a, b) => b.creation - a.creation).reverse();

        return (
            <div>
                <sup className="discrete">Last Updated: {this.state.lastUpdated.toLocaleString()}</sup>

                {this.state.entries.map((entry, index) => (
                    <div className="card" key={this.entryCardKeyPrefix+index}>
                        <div className="card-body" key={this.entryCardKeyPrefix+"body"+index}>
                            <h5 className="card-title" key={this.entryCardKeyPrefix+"title"+index}>entry {index}</h5>
                            <h6 className="card-subtitle mb-2 text-muted" key={this.entryCardKeyPrefix+"subtitle"+index}><i>{new Date(entry.creation).toLocaleString()}</i></h6>
                            <p className="card-text" key={this.entryCardKeyPrefix+"text"+index}>{entry.body}</p>
                        </div>
                    </div>
                ))}
            </div>
        )
    }
}

export default JournalEntries