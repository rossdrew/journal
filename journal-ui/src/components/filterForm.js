import React, { Component } from 'react'

class FilterForm extends Component {
    filterString() {
        if (!this.state ||  this.state.length === 0){
            return "contains=" + this.state.containsFilter + "&";
        }
    }

    render() {
        return (
            <div onSubmit={this.props.entriesRefresh}>
                <form id="filterForm">
                    <input className="form-control form-control-sm"
                           id="filterByContent"
                           type="text"
                           placeholder="Filter entries..."
                           name="containsFilter"
                           value={ this.props.value }
                           onChange={ this.props.entriesStateChange } />
                </form>
            </div>
        )
    }
}

export default FilterForm