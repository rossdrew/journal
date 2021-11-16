import React, { Component } from 'react'

class JournalEntry extends Component {

    replaceLinks(markdownText){
        //replace the linebreaks with <br>
        var parsedText = markdownText.replace(/(?:\r\n|\r|\n)/g, '<br>');
        //check for links [text](url)
        let elements = parsedText.match(/\[.*?\)/g);
        if( elements != null && elements.length > 0){
            for(const el of elements){
                let txt = el.match(/\[(.*?)\]/)[1];//get only the txt
                let url = el.match(/\((.*?)\)/)[1];//get only the link
                parsedText = parsedText.replace(el,'<a href="'+url+'" target="_blank">'+txt+'</a>');
            }
        };
        return parsedText;
    }

    markdownToHTML(markdownText) {
        let markdownWithLinks = this.replaceLinks(markdownText)
        return (
            markdownWithLinks
        )
    }

    render() {
        return (
            <div className="card w-100 p-0" key={this.props.keyPrefix+"content"}>
                <div className="card-body">
                    <sup key={this.props.keyPrefix+"date"}
                         className="entry-date">
                        {new Date(this.props.entry.creation).toLocaleString()}
                    </sup>

                    <p key={this.props.keyPrefix+"body"}
                       className="entry-body">
                        {/*Not secure & we need to validate input*/}
                        { <div dangerouslySetInnerHTML={{__html: this.markdownToHTML(this.props.entry.body)}} /> }
                    </p>

                    <sub key={this.props.keyPrefix+"meta"}
                         className="entry-meta debug">
                        Index: {this.props.index}
                    </sub>
                </div>
            </div>
        )
    }
}


export default JournalEntry