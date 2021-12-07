import React, { Component } from 'react'
import HeaderControl from "./headerControl";
import JournalEntry from "./journalEntry";
import FootControl from "./footControl";

class JournalEntries extends Component {
    constructor() {
        super();
        this.state = {
             entryBlockSize: null, //Size of entries blocks to request
             entryBufferBlocks: 0, //Number of blocks each side of the displayed one to buffer
             previousEntries: [],  //Buffer blocks backwards in time
             nextEntries: [],      //Buffer blocks forwards in time

             entries: [],
             entriesPagingHeader: {
                 size: null,
                 limit: null,
                 start: null
             },

             containsFilter: "",
             entryStartIndex: null,
             entryLimit: null,

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

    trackScrolling = () => {
        const wrappedElement = document.getElementById('infiniteScroller');
        if (this.isBottomOf(wrappedElement)) {
            console.log('Edge of loaded entries reached...');
            document.removeEventListener('scroll', this.trackScrolling);
            //TODO Load new entries
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
                entriesPagingHeader: {
                    size: pagedData.size,
                    limit: pagedData.limit,
                    start: pagedData.startIndex
                },
                lastUpdated: new Date()
            })
        }).catch(console.log);

        //fill buffer, TODO conditions to make sure it's filled or not filled when corect
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
        if (this.state.entriesPagingHeader.size > (this.state.entriesPagingHeader.start + this.state.entriesPagingHeader.limit)){
            return true
        }else {
            return false
        }
    }

    render() {
        //Ordered by date, latest at top - why doesn't this work in the refresh method?
        this.state.entries.sort((a, b) => b.creation - a.creation).reverse();

        return (
            <div className="w-100 d-flex justify-content-around">
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