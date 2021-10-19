import './App.css';
import React, {Component} from 'react';

import EntryForm from './components/entry-form';
import JournalEntries from "./components/journal-entries";

class App extends Component {
    state = {
        lastUpdated: "unknown"
    }

    render() {
        return (
            <div className="App-header">
                <JournalEntries />
                <EntryForm />
            </div>
        )
    }
}

export default App;
