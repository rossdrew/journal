import React, { Component } from 'react'

class FilterForm extends Component {
    constructor() {
        super();
        this.state = {
            containsFilter: ""
        }

        this.filterChange = this.filterChange.bind(this);
    }

    filterString() {
        if (!this.state ||  this.state.length === 0){
            return "contains=" + this.state.containsFilter + "&";
        }
    }

    render() {
        return (
            <div>
                <div className="form-group navbar navbar-dark bg-dark fixed-top cflex">
                    <form onSubmit={this.publish}>
                        <input className="form-control"
                               id="search"
                               type="text"
                               name="containsFilter"
                               value={ this.state.containsFilter }
                               onChange={ this.filterChange } />
                        <button type="submit" value="Send">Filter</button>
                    </form>
                </div>
            </div>
        )
    }
}

export default FilterForm