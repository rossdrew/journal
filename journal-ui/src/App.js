import './App.css';
import React, {Component} from 'react';

import JournalEntries from "./components/journalEntries";
import FootControl from "./components/foot/footControl";

class App extends Component {
    state = {
        lastUpdated: "unknown"
    }

    entries: React.RefObject<JournalEntries>;

    constructor(props: any){
        super(props);

        this.child = React.createRef();
    }

    render() {
        return (
            <div className="App-header">
                <JournalEntries key="entry_display" ref={this.entries} />
                <FootControl />
            </div>
        )
    }
}

export default App;
