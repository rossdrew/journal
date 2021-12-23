import React, { Component } from 'react'
import HeaderControl from "./headerControl";
import JournalEntry from "./journalEntry";
import FootControl from "./footControl";

class JournalEntries extends Component {
    constructor() {
        super();
        this.state = {
             entries: [],
             loadedEntries: {
                 start: 0
             },
             entriesPagingHeader: {
                 size: null,
                 limit: null,
                 start: null
             },

             containsFilter: "",
             entryStartIndex: null,
             entryLimit: 5,

             activeFilter: "",
             lastUpdated: "unknown",

             entryPreview: null
        }
        this.entryCardKeyPrefix = "entry-card-"

        this.stateChange = this.stateChange.bind(this);
    }

    componentDidMount() {
        this.refresh()
        document.addEventListener('scroll', this.trackScrolling);
    }

    isBottomOf(element) {
        if (element)
            return element.getBoundingClientRect().bottom <= window.innerHeight - 100;
    }

    componentWillUnmount() {
        document.removeEventListener('scroll', this.trackScrolling);
    }

    elementCount() {
        if (typeof this.state.entries !== 'undefined')
            return this.state.entries.length
        else
            return 0
    }

    //XXX Could do with some cleanup
    appendEntries() {
        document.removeEventListener('scroll', this.trackScrolling);

        let indexOfNewEntries = this.state.loadedEntries.start + this.elementCount()
        this.getEntries({
            contains : this.state.containsFilter,
            start : indexOfNewEntries,
            limit : this.state.entryLimit
        }).then((pagedData) => {
            this.setState({
                entries: this.state.entries.concat(pagedData.data),
            })
        }).catch(console.log);

        document.addEventListener('scroll', this.trackScrolling);
    }

    trackScrolling = () => {
        const wrappedElement = document.getElementById('infiniteScroller');
        //XXX Fetches and appends new antries
        if (this.isBottomOf(wrappedElement)) {
            this.appendEntries()
        }
    };

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

    getEntries({start, limit, contains}){
        let url = 'http://localhost:8080/entries?';
        if (contains) url = url.concat("contains=" + contains + "&");
        if (start) url = url.concat("start=" + start + "&");
        if (limit) url = url.concat("limit=" + limit + "&");

        return fetch(url)
            .then(res => res.json())
            .catch(console.log);
    }

    refresh(event) {
        if (event) {
            event.preventDefault()
        }

        this.getEntries({
            contains : this.state.containsFilter,
            start : this.state.entryStartIndex,
            limit : this.state.entryLimit
        }).then((pagedData) => {
            this.setState({
                entries: pagedData.data,
                loadedEntries: {
                    start: pagedData.startIndex
                },
                entriesPagingHeader: {
                    size: pagedData.size,
                    limit: pagedData.limit,
                    start: pagedData.startIndex
                },
                lastUpdated: new Date(),
            })
        }).catch(console.log);

        this.setState({
            activeFilter: this.state.containsFilter
        })
    }

    entriesRemaining(){
        return (this.state.entries.length < this.state.entriesPagingHeader.size)
    }

    render() {
        return (
            <div className="w-100 d-flex justify-content-around">
                <div className="w-50 entry-listing">
                    <HeaderControl entryStateChange={this.stateChange}
                                   containsFilter={this.state.containsFilter}
                                   refresh={(event) => this.refresh(event)} />

                    <sup className="discrete">
                        Last Updated: {this.state.lastUpdated.toLocaleString()} {(this.state.activeFilter) ? ", Filtered by '" + this.state.activeFilter + "'" : ""}
                    </sup>

                    {/*{this.preview()}*/}

                    {this.state.entries.map((entry, index) => (
                        <JournalEntry entry={entry}
                                      index={index}
                                      keyPrefix={this.entryCardKeyPrefix + index}
                                      key={this.entryCardKeyPrefix + index} />
                    ))}

                    <div className="continue" id="infiniteScroller">
                        { this.entriesRemaining() ? "..." : "."}
                    </div>
                </div>
                <FootControl />
            </div>
        )
    }
}

export default JournalEntries