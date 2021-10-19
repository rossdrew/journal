import './App.css';
import React, {Component} from 'react';

import Entries from './components/entries';
import EntryForm from './components/entry-form';

class App extends Component {
    state = {
        entries: []
    }

    componentDidMount() {
        fetch('http://localhost:8080/entries')
            .then(res => res.json())
            .then((data) => {
                this.setState({ entries: data })
            })
            .catch(console.log)
    }

    render() {
        return (
            <div className="App-header">
                <Entries entries={this.state.entries} />
                <EntryForm />
            </div>
        )
    }
}

export default App;
