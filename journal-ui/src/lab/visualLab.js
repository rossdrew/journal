import React, { Component } from 'react'

class VisualLab extends Component {
    constructor() {
        super();
        this.state = {}
    }

    render() {
        const div1StartY = 100;
        const div1Height = 100;

        const div2StartY = div1StartY + div1Height;
        const div2Height = 100;

        const div3StartY = div1StartY + (div1Height/2);
        const div3Height = (div1Height/2) + (div2Height/2);

        return (
            <div id="taskStream">
                <div id="task1" style={{ position: 'absolute', top: div1StartY, left: '100px', height: div1Height+"px", width:'100px', backgroundColor: '#573f00' }}>
                    Task 1
                </div>

                <div id="task2" style={{ position: 'absolute', top: div2StartY, left: '100px', height: div2Height+"px", width:'100px', backgroundColor: '#453200' }}>
                    Task 2
                </div>

                <div id="linkTask1to2" style={{ position: 'absolute', top: div3StartY, left: '200px', height: div3Height+"px", width: "100px"}} className="task-link">
                    Link
                </div>

            </div>
        )
    }
}

export default VisualLab