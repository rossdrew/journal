import React, { Component } from 'react'
import HeaderControl from "./headerControl";
import JournalEntry from "./journalEntry";
import FootControl from "./footControl";
import SizedHeadTailLinkedList from "../utilities/SizedHeadTailLinkedList";

class BufferedJournalEntries extends Component {
    constructor() {
        super();
        this.state = {
            loadedEntries: {
                //Start index of loaded entries
                start: 0,
                //Buffer of entries loaded
                entries: new SizedHeadTailLinkedList(20)
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

    //XXX Could do with some cleanup
    appendEntries() {
        document.removeEventListener('scroll', this.trackScrolling);

        let nextElementIndex = this.state.loadedEntries.start + this.elementCount()
        this.getEntries({
            contains : this.state.containsFilter,
            start : nextElementIndex,
            limit : this.state.entryLimit
        }).then((pagedData) => {
            this.setState({
                entryBuffer: this.state.loadedEntries.entries.append(pagedData.data).deepClone(),
            })
        }).catch(console.log);

        document.addEventListener('scroll', this.trackScrolling);
    }

    trackScrolling = () => {
        const wrappedElement = document.getElementById('infiniteScroller');
        //XXX Fetches and appends new entries
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
        }).then((pagedEntries) => {
            let updatedEntries = this.state.loadedEntries.entries.deepClone();
            pagedEntries.data.forEach(entry => {
                updatedEntries.append(entry);
            }); //XXX this wont always be an append

            console.log(updatedEntries.getSize() + " entries loaded..")

            this.setState({
                loadedEntries: {
                    start: pagedEntries.startIndex,
                    entries: updatedEntries
                },
                entriesPagingHeader: {
                    size: pagedEntries.size,
                    limit: pagedEntries.limit,
                    start: pagedEntries.startIndex
                },
                lastUpdated: new Date(),
            })
        }).catch(console.log);

        this.setState({
            activeFilter: this.state.containsFilter
        })
    }

    elementCount() {
        if (typeof this.state.loadedEntries.entries !== 'undefined') {
            return typeof this.state.loadedEntries.entries.getSize()
        }else {
            return 0
        }
    }

    entriesRemaining(){
        return (this.elementCount() < this.state.entriesPagingHeader.size)
    }

    loadedEntrySet(){
        if (this.elementCount() > 0) {
            return this.state.loadedEntries.entries.asCollection();
        }else {
            return [];
        }
    }

    render() {
        this.loadedEntrySet().forEach(e => console.log(e))

        return (
            <div className="w-100 d-flex justify-content-around">
                <div className="w-50 entry-listing">
                    <HeaderControl entryStateChange={this.stateChange}
                                   containsFilter={this.state.containsFilter}
                                   refresh={(event) => this.refresh(event)} />

                    <sup className="discrete">
                        Last Updated: {this.state.lastUpdated.toLocaleString()} {
                            (this.state.activeFilter) ? ", Filtered by '" + this.state.activeFilter + "'" : ""
                        }
                    </sup>

                    {/*{this.preview()}*/}

                    {this.loadedEntrySet().map((entry, index) => (
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

export default BufferedJournalEntries