import React, { Component } from 'react'

class HeaderControl extends Component {
    state = {
        lastUpdated: "unknown"
    }

    render() {
        return (
            <nav className="navbar fixed-top navbar-light bg-dark">
                <div className="container-fluid">
                    <div className="row container-fluid ">
                        <div className="col-sm-10 d-flex justify-content-end">
                            <form onSubmit={this.publish} id="filterForm">
                                <input className="form-control form-control-sm"
                                       id="search"
                                       type="text"
                                       placeholder="Filter entries..."
                                       name="containsFilter"
                                       required/>
                            </form>
                        </div>

                        <div className="col-sm-2 d-flex justify-content-end">
                            <button type="submit" className="btn btn-sm btn-block btn-outline-light" form="filterForm">Filter</button>
                        </div>
                    </div>
                </div>
            </nav>
        )
    }
}

export default HeaderControl