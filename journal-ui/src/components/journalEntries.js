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

    trackScrolling = () => {
        const wrappedElement = document.getElementById('infiniteScroller');
        if (this.isBottomOf(wrappedElement)) {
            console.log('Edge of loaded entries reached...');
            document.removeEventListener('scroll', this.trackScrolling);

            //TODO Load new entries cleanup
            let indexOfNewEntries = this.state.loadedEntries.start + this.elementCount()

            console.log("Starting at " + this.state.loadedEntries.start + ", " + this.elementCount() + " elements stored.  Requesting another " + this.state.entriesPagingHeader.limit + ".  New elements starting from " + indexOfNewEntries)
            console.log("Getting [" + indexOfNewEntries + ".." + (this.state.entryLimit+indexOfNewEntries) + "] (" + this.state.entryLimit +  " entries)")

            this.getEntries({
                contains : this.state.containsFilter,
                start : indexOfNewEntries,
                limit : this.state.entryLimit
            }).then((pagedData) => {
                console.log(pagedData)
                console.log("...got " + pagedData.data.length + " new elements!")
                this.setState({
                    entries: this.state.entries.concat(pagedData.data),
                })

            }).catch(console.log);

            //------TODO--------------

            document.addEventListener('scroll', this.trackScrolling);
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

        console.log("Making request '" + url + "'")

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
                lastUpdated: new Date()
            })
        }).catch(console.log);

        //fill buffer, TODO conditions to make sure it's filled or not filled when correct
        // this.getEntries({
        //     contains : this.state.containsFilter,
        //     start : this.state.entryStartIndex + this.state.entryLimit,
        //     limit : this.state.entryLimit
        // }).then((pagedData) => {
        //     this.setState({
        //         nextEntries: pagedData.data
        //     })
        // }).catch(console.log);

        this.setState({
            activeFilter: this.state.containsFilter
        })
    }

    entriesRemaining(){
        console.log(this.state.entries.length + " entries of " + this.state.entriesPagingHeader.size + " available.")
        return (this.state.entries.length < this.state.entriesPagingHeader.size)
        // let latestLoadedEntries = (this.state.entriesPagingHeader.start + this.state.entriesPagingHeader.limit)
        // return (this.state.entriesPagingHeader.size > latestLoadedEntries);
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