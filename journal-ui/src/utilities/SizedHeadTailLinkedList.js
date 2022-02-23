class SizedHeadTailLinkedList {
    constructor(size) {
        this.first = null;
        this.last = null;
        this.maxSize = size;
        this.size = 0;
    }

    verifySize(){
        let count = 0;
        let index = this.first;

        while (index){
            count++;
            index = index.next
        }

        return count;
    }

    getSize(){
        return this.size;
    }

    append(element){
        let newElement = new ElementNode(element)
        if (!this.first){
            this.first = newElement;
            this.last = newElement;
            this.size = 1;
            return;
        }

        // (f,..,l) -> (f,..,l,n)
        newElement.setPrevious(this.last)
        this.last.setNext(newElement);
        this.last = newElement;
        this.size++;

        //Burn top element
        if (this.size > this.maxSize){
            let newFirst = this.first.next;
            this.first.next = null;
            newFirst.previous = null;
            this.first = newFirst;
            this.size--;
        }
    }

    prepend(element){
        let newElement = new ElementNode(element)
        if (!this.first){
            this.first = newElement;
            this.last = newElement;
            this.size = 1;
            return;
        }

        // (f,..,l) -> (n,f,..,l)
        newElement.setNext(this.first);
        this.first.setPrevious(newElement);
        this.first = newElement;
        this.size++;

        //Burn bottom element
        if (this.size > this.maxSize){
            let newLast = this.last.previous;
            this.last.setPrevious(null);
            newLast.next = null;
            this.last = newLast;
            this.size--;
        }
    }

    deepClone(){
        let clonedList = new SizedHeadTailLinkedList(this.maxSize);
        let n = this.first;
        while (n){
            clonedList.append(n.getElement());
            n = n.getNext();
        }
        return clonedList;
    }

    asCollection(){
        let n = this.first;
        let constructedList = [];
        while (n){
            constructedList.push(n.getElement());
            n = n.getNext();
        }
        return constructedList;
    }
}

class ElementNode {
    constructor(element) {
        this.element = element;
        this.previous = null;
        this.next = null;
    }

    setNext(n){
        this.next = n;
    }

    setPrevious(p){
        this.previous = p;
    }

    getNext(){
        return this.next;
    }

    getPrevious(){
        return this.previous;
    }

    getElement(){
        return this.element;
    }
}


export default SizedHeadTailLinkedList