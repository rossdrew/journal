import './App.css';
import React, {Component} from 'react';

import EntryCreationForm from './components/entry-creation-form';
import JournalEntries from "./components/journal-entries";

class App extends Component {
    state = {
        lastUpdated: "unknown"
    }

    render() {
        return (
            <div className="App-header">
                <JournalEntries />
                <EntryCreationForm />
            </div>
        )
    }
}

export default App;
