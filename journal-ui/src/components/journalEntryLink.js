import React, { Component } from 'react'

// Get a list of tasks outstanding
// For each pair, separate those on screen and those off, with directionality of link

class JournalEntryLink extends Component {
    render() {
        return (
            <div className="debug">&nbsp;&nbsp;&nbsp;[{this.props.index}] '{this.props.task.id}'</div>
        )
    }
}

export default JournalEntryLink