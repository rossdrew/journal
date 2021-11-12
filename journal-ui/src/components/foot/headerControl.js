import React, { Component } from 'react'
import FilterForm from "./filterForm";

class HeaderControl extends Component {
    state = {
        lastUpdated: "unknown"
    }

    render() {
        return (
            <nav className="navbar fixed-top navbar-light">
                <div className="container-fluid">
                    <div className="row container-fluid d-flex flex-nowrap ">
                        <div className="col-sm-6 " />

                        <div className="col-sm-4 ">
                            <FilterForm entriesStateChange={this.props.entryStateChange}
                                        value={this.props.containsFilter}
                                        entriesRefresh={this.props.refresh} />
                        </div>

                        <div className="col-sm-2 d-flex">
                            <button type="submit"
                                    className="btn btn-sm btn-block btn-light"
                                    form="filterForm">Filter</button>
                        </div>
                    </div>
                </div>
            </nav>
        )
    }
}

export default HeaderControl