import React from 'react'

const Entries = ({ entries }) => {
    return (
        <div>
            <h1>Journal Entries</h1>

            {entries.map((entry, index) => (
                <div className="card">
                    <div className="card-body">
                        <h5 className="card-title">entry {index}</h5>
                        <h6 className="card-subtitle mb-2 text-muted"><i>{new Date(entry.creation).toLocaleString()}</i></h6>
                        <p className="card-text">{entry.body}</p>
                    </div>
                </div>
            ))}
        </div>
    )
};

export default Entries