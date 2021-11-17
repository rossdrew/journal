import React, { Component } from 'react'

class JournalEntry extends Component {

    replaceImplicitLinks(markdownText) {
        console.log(markdownText)
        var parsedText = markdownText;
        let pazLinks = parsedText.match(/PAZ-\d*/g);
        console.log("Links: " + pazLinks)
        if (pazLinks && pazLinks.length > 0){
            for (const link in pazLinks){
                console.log("Link: " + link)
                let ticketNumber = link.match(/PAZ-\d*/)
                console.log("Ticket Number: " + ticketNumber)
                parsedText = parsedText.replace(ticketNumber,'<a href="https://jira.pingidentity.com/browse/'+ticketNumber+'" target="_blank">'+ticketNumber+'</a>');
            }
        }
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
        return parsedText;
    }

    markdownToHTML(markdownText) {
        return (
            this.replaceLinks(markdownText)
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