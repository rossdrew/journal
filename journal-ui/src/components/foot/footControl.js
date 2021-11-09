import React, { Component } from 'react'
import EntryForm from "./entryForm";

class FootControl extends Component {
    state = {
        lastUpdated: "unknown"
    }

    render() {
        return (
            <nav className="navbar fixed-bottom navbar-light bg-dark">
                <div className="container-fluid"> {/*Grid*/}
                    <div className="row container-fluid">
                        <div className="col-sm-8">
                            <EntryForm />
                        </div>

                        <div className="col-sm-4">
                            <button type="submit" className="btn btn-sm btn-block btn-outline-light" form="journalEntryForm">Create</button>
                            <br/>
                            <div className="debug">
                                React v{React.version} | JavaScript v{window.jsver}
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
        )
    }
}

export default FootControl