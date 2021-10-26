import './App.css';
import React, {Component} from 'react';

import EntryCreationForm from './components/entry-creation-form';
import JournalEntries from "./components/journal-entries";

class App extends Component {
    state = {
        lastUpdated: "unknown"
    }

    entries: React.RefObject<JournalEntries>;
    entrycreation: React.RefObject<EntryCreationForm>;

    constructor(props: any){
        super(props);

        this.child = React.createRef();
    }

    render() {
        return <div className="App-header">
            <JournalEntries key="entry_display" ref={this.entries} />

            <div className="navbar fixed-bottom navbar-dark bg-dark">
                <EntryCreationForm key="entry_creation" ref={this.entrycreation} requestRefresh={() => {
                    this.entries.current?.refresh();
                }}
                />

                {/*Should probably be a component*/}
                <div className="debug">
                    React v{React.version} | JavaScript v{window.jsver}
                </div>
            </div>
        </div>
    }
}

export default App;
