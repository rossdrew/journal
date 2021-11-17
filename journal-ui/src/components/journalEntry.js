import React, { Component } from 'react'

class JournalEntry extends Component {

    replaceImplicitLinks(markdownText) {
        var parsedText = markdownText.replace(/(PAZ-\d*)/g,"<a href='https://jira.pingidentity.com/browse/$1' target='_blank'>$1</a>");
        return parsedText
    }

    /**
     * Replace all markdown of the form `[text](url)` with HTML links
     * of the form `<a href='url'>text</a>`
     *
     * @param markdownText text containing markdown links
     * @returns 'markdownText' with markdown links replaced with HTML
     */
    replaceLinks(markdownText){
        //replace the linebreaks with <br>
        var parsedText = markdownText.replace(/(?:\r\n|\r|\n)/g, '<br>');
        //check for links of the form [text](url)
        let elements = parsedText.match(/\[.*?\)/g);
        if(elements != null && elements.length > 0){
            for(const el of elements){
                let linkText = el.match(/\[(.*?)\]/)[1];
                let url = el.match(/\((.*?)\)/)[1];
                parsedText = parsedText.replace(el,'<a href="'+url+'" target="_blank">'+linkText+'</a>');
            }
        };
        return this.replaceImplicitLinks(parsedText);
    }

    markdownToHTML(markdownText) {
        return (
            this.replaceLinks(markdownText)
        )
    }

    render() {
        return (
            <div className="card w-100 p-0" key={this.props.keyPrefix+"content"}>
                <div className="card-header">
                    <div key={this.props.keyPrefix+"date"}
                         className="entry-date">
                        {new Date(this.props.entry.creation).toLocaleString()}
                    </div>
                </div>

                <div className="card-body">
                    <div key={this.props.keyPrefix+"body"}
                       className="entry-body">
                        {/*XXX Not secure & we need to validate input*/}
                        { <div dangerouslySetInnerHTML={{__html: this.markdownToHTML(this.props.entry.body)}} /> }
                    </div>

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