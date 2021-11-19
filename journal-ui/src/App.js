import './App.css';
import React, {Component} from 'react';

import JournalEntries from "./components/journalEntries";

class App extends Component {
    state = {
        lastUpdated: "unknown"
    }

    testCanvasDrawing(){
        const canvas = this.refs.outbox
        const ctx = canvas.getContext("2d")

        ctx.drawImage(canvas, 0, 0)
        ctx.font = "12px Courier"
        ctx.fillText("OUTBOX", 210, 75)

        ctx.beginPath();
        ctx.moveTo(10, 10);
        ctx.lineTo(100, 20);
        ctx.lineTo(10, 40);
        ctx.strokeStyle = 'white';
        ctx.lineWidth = 1;
        ctx.stroke();
    }

    componentDidMount() {
       //this.testCanvasDrawing();
    }

    constructor(props: any){
        super(props);

        this.updateDraft = (newDraft) => {
            this.setState(state => ({
                draftEntry: newDraft
            }))
        }
    }


    render() {
        return (
            <div className="App-header " >
                <JournalEntries key="entry_display" ref={this.entries} />
                {/*<canvas ref="outbox" />*/}
            </div>
        )
    }
}

export default App;
