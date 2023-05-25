import React, { Component } from 'react'
import HeaderControl from "./headerControl";
import JournalEntry from "./journalEntry";
import FootControl from "./footControl";
import SizedHeadTailLinkedList from "../utilities/SizedHeadTailLinkedList";
import JournalEntryLink from "./journalEntryLink";

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

            loadedTasks: {
                tasks: []
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

    isBottomOfScreen(element) {
        if (element)
            return element.getBoundingClientRect().top <= window.innerHeight - 100;
    }

    isTopOfScreen(element) {
        if (element)
            return element.getBoundingClientRect().bottom > 0;
    }

    componentWillUnmount() {
        document.removeEventListener('scroll', this.trackScrolling);
    }

    //XXX Could do with some cleanup
    prependEntries() {
        document.removeEventListener('scroll', this.trackScrolling);

        console.log("TODO: Prepend entries")

        document.addEventListener('scroll', this.trackScrolling);
    }

    //XXX Could do with some cleanup
    appendEntries() {
        document.removeEventListener('scroll', this.trackScrolling);

        let startIndexOfAppendedEntries = this.state.loadedEntries.start + this.elementCount();

        this.getEntries({
            contains : this.state.containsFilter,
            start : startIndexOfAppendedEntries,
            limit : this.state.entryLimit
        }).then((pagedResponseData) => {
            let updatedEntries = this.state.loadedEntries.entries.deepClone();
            let newStartIndex = this.state.loadedEntries.start + ((this.state.loadedEntries.entries.getSize() + pagedResponseData.data.length) - this.state.loadedEntries.entries.getLimit())

            pagedResponseData.data.forEach(pagedDataEntry => {
                updatedEntries.append(pagedDataEntry);
            });

            this.setState({
                loadedEntries: {
                    entries: updatedEntries,
                    start: (newStartIndex < 0) ? 0 : newStartIndex
                },
            })
        }).catch(console.log);

        document.addEventListener('scroll', this.trackScrolling);
    }

    trackScrolling = () => {
        const prependElement = document.getElementById('infiniteScrollerPrepend');
        const appendElement = document.getElementById('infiniteScrollerAppend');
        //XXX Fetches and appends new entries
        if (this.isBottomOfScreen(appendElement)) {
            this.appendEntries()
        }else if (this.isTopOfScreen(prependElement)){
            this.prependEntries()
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

    getTasks(){
        let url = 'http://localhost:8080/tasks';

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
            }); //XXX this won't always be an append operation

            // console.log(updatedEntries.getSize() + " entries loaded from server")

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

        //XXX Why do I need to manually map from an array to an array
        this.getTasks().then((responseTasks) => {
            let intemediateTaskCollection = [];
            responseTasks.data.forEach(entry => {
                intemediateTaskCollection = intemediateTaskCollection.concat(entry);
            });
            this.setState({
                loadedTasks: {
                    tasks: intemediateTaskCollection
                }
            })

            console.log("TEST")
            console.log(intemediateTaskCollection)
            console.log(responseTasks.data)
            console.log(this.state.loadedTasks.tasks)
            console.log("TEST")
        })

        this.setState({
            activeFilter: this.state.containsFilter
        })
    }

    elementCount() {
        if (typeof this.state.loadedEntries.entries !== 'undefined') {
            return this.state.loadedEntries.entries.getSize()
        }else {
            return 0
        }
    }

    appendableEntriesRemaining(){
        return (this.state.loadedEntries.start + this.elementCount() < this.state.entriesPagingHeader.size)
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

                    {/*Is there a better way to do this?*/}
                    <div className="continue" id="infiniteScrollerPrepend">
                        { this.state.loadedEntries.start > 0 ? "..." : ""}
                    </div>

                    {this.preview()}

                    {this.loadedEntrySet().map((entry, index) => (
                        <JournalEntry entry={entry}
                                      index={index}
                                      keyPrefix={this.entryCardKeyPrefix + index}
                                      key={this.entryCardKeyPrefix + index} />
                    ))}

                    {/* WIP  WIP  WIP  WIP  WIP  WIP  WIP  WIP  WIP */}
                    {/*<div class="debug"><b><u>Task Debugging</u></b><br/>{this.state.loadedTasks.tasks.length} Items...</div>*/}
                    {/*{this.state.loadedTasks.tasks.map( (task, index) =>*/}
                    {/*    <JournalEntryLink task={task} entries={this.state.loadedEntries} index={index}/>*/}
                    {/*)}*/}

                    <div className="continue" id="infiniteScrollerAppend">
                        { this.appendableEntriesRemaining() ? "..." : "."}
                    </div>
                </div>
                <FootControl />
            </div>
        )
    }
}

export default BufferedJournalEntries