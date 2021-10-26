import React, { Component } from 'react'

class JournalEntries extends Component {
    constructor() {
        super();
        this.state = {
            entries: [],
            containsFilter: "",
            lastUpdated: "unknown"
        }
        this.entryCardKeyPrefix = "entry-card-"

        this.filterChange = this.filterChange.bind(this);
    }

    componentDidMount() {
        this.refresh()
        // This is clearing state so not using filters
    }

    filterChange({target}){
        this.setState({
            [target.name]: target.value
        });
    }

    refresh() {
        let url = 'http://localhost:8080/entries?';
        if (!this.state ||  this.state.length === 0){
            url = url.concat("contains=" + this.state.containsFilter + "&");
        }

        console.log("Requesting " + url)

        fetch(url)
            .then(res => res.json())
            .then((data) => {
                this.setState({
                    entries: data,
                    lastUpdated: new Date()
                })
            }).catch(console.log);
    }

    publish() {
        this.refresh()
    }

    render() {
        //Ordered by date, latest at top - why doesn't this work in the refresh method?
        this.state.entries.sort((a, b) => b.creation - a.creation).reverse();

        return (
            <div>
                {/*Should probably be a component*/}
                <div className="form-group navbar navbar-dark bg-dark fixed-top cflex">
                    <form onSubmit={this.publish}>
                        <input className="form-control"
                               id="search"
                               type="text"
                               name="containsFilter"
                               value={ this.state.containsFilter }
                               onChange={ this.filterChange } />
                         <button type="submit" value="Send">Filter</button>
                    </form>
                </div>

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