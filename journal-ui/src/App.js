import './App.css';
import React, {Component} from 'react';

import JournalEntries from "./components/journalEntries";

class App extends Component {
    state = {
        lastUpdated: "unknown"
    }

    componentDidMount() {
       //this.testCanvasDrawing();
    }

    constructor(props: any){
        super(props);

        this.updateDraft = (newDraft) => {
            this.setState(state => ({
                draftEntry: newDraft
            }))
        }
    }


    render() {
        return (
            <div className="App-header" >
                <JournalEntries key="entry_display" ref={this.entries} />
            </div>
        )
    }
}

export default App;
