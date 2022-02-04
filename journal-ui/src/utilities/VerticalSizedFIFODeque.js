class VerticalSizedFIFODeque {
    constructor(size) {
        this.first = null;
        this.last = null;
        this.maxSize = size;
        this.size = 0;
    }

    verifySize(){
        let count = 0;
        let index = this.first;

        while (index != null){
            count++;
            index = index.next
        }

        return count;
    }

    append(element){
        let newElement = ElementNode(element)
        if (this.first == null){
            this.first = newElement;
            this.last = newElement;
            return;
        }

        // (..,l) -> (..,l,n)
        newElement.next = this.last
        this.last.next = newElement;
        this.last = newElement;

        //Burn element at the top
        if (this.size > this.maxSize){
            let newFirst = this.first.next;
            this.first.next = null;
            newFirst.previous = null;
            this.first = newFirst;
        }
    }

    prepend(element){
        let newElement = ElementNode(element)
        if (this.first == null){
            this.first = newElement;
            this.last = newElement;
            return;
        }

        // (f,..) -> (n,f,..)
        newElement.next = this.first
        this.first.previous = newElement
        this.first = newElement

        //Burn element at the bottom
        if (this.size > this.maxSize){
            let newLast = this.last.previous
            this.last.previous = null;
            newLast.next = null;
            this.last = newLast;
        }
    }
}

class ElementNode {
    constructor(element) {
        this.element = element;
        this.previous = null;
        this.next = null;
    }
}