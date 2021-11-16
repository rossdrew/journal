import React, { Component } from 'react'
import EntryForm from "./entryForm";

class FootControl extends Component {
    render() {
        return (
            <nav className="navbar fixed-bottom navbar-light bg-dark">
                <div className="container-fluid"> {/*Grid*/}
                    <div className="row container-fluid p-0">
                        <div className="col-sm-8 p-0 w-100">
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