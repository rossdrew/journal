import React, { Component } from 'react'
import HeaderControl from "./headerControl";
import JournalEntry from "./journalEntry";

class JournalEntries extends Component {
    constructor() {
        super();
        this.state = {
            entries: [],
            containsFilter: "",
            activeFilter: "",
            lastUpdated: "unknown",
           entryPreview: null
        }
        this.entryCardKeyPrefix = "entry-card-"

        this.stateChange = this.stateChange.bind(this);
    }

    componentDidMount() {
        this.refresh()
        // This is clearing state so filters not being used
    }

    stateChange({target}){
        this.setState({
            [target.name]: target.value
        });
    }

    preview(){
        if (this.state.entryPreview){
            return <JournalEntry entry={{"body":"test", "creation": "preview"}}
                          index="PREVIEW"
                          keyPrefix="preview"
                          key="preview" />
        }else{
            return ""
        }
    }

    refresh(event) {
        if (event) {
            event.preventDefault()
        }
        let url = 'http://localhost:8080/entries?';
        if (this.state.containsFilter){
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

        this.setState({
            activeFilter: this.state.containsFilter
        })
    }

    render() {
        //Ordered by date, latest at top - why doesn't this work in the refresh method?
        this.state.entries.sort((a, b) => b.creation - a.creation).reverse();

        return (
            <div className="w-50 entry-listing">
                <HeaderControl entryStateChange={this.stateChange}
                               containsFilter={this.state.containsFilter}
                               refresh={(event) => this.refresh(event)} />

                <sup className="discrete">
                    Last Updated: {this.state.lastUpdated.toLocaleString()} {(this.state.activeFilter) ? ", Filtered by '" + this.state.activeFilter + "'" : ""}
                </sup>

                {this.preview()}

                {this.state.entries.map((entry, index) => (
                    <JournalEntry entry={entry}
                                  index={index}
                                  keyPrefix={this.entryCardKeyPrefix + index}
                                  key={this.entryCardKeyPrefix + index} />
                ))}

                <div className="continue">.</div>
            </div>
        )
    }
}

export default JournalEntries