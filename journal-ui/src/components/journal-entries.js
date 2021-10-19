import React, { Component } from 'react'

class JournalEntries extends Component {
    state = {
        entries: [],
        lastUpdated: "unknown"
    }

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
        return (
            <div>
                <h1>Journal Entries</h1>

                {this.state.entries.map((entry, index) => (
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">entry {index}</h5>
                            <h6 className="card-subtitle mb-2 text-muted"><i>{new Date(entry.creation).toLocaleString()}</i></h6>
                            <p className="card-text">{entry.body}</p>
                        </div>
                    </div>
                ))}
                <sup className="discrete">Last Updated: {this.state.lastUpdated.toLocaleString()}</sup>
            </div>
        )
    }
}

export default JournalEntries