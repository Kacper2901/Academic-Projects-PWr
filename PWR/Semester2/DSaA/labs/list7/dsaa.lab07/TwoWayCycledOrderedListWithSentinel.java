package dsaa.lab07;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E> implements IList<E>{

    private class Element{

        public Element(E e) {
            object = e;
            next = null;
            prev = null;
        }
        public Element(E e, Element next, Element prev) {
            object = e;
            this.next = next;
            this.prev = prev;
        }
        // add element e after this
        public void addAfter(Element elem) {

            Element next = this.next;
            this.next = elem;
            this.next.prev = this;
            this.next.next = next;
            this.next.next.prev = this.next;

        }
        // assert it is NOT a sentinel
        public void remove() {
            this.prev.next = this.next;
            this.next.prev = this.prev;

        }
        E object;
        Element next=null;
        Element prev=null;
    }


    Element sentinel;
    int size;

    private class InnerIterator implements Iterator<E>{
        Element curr;
        public InnerIterator() {
            curr = sentinel.next;
        }
        @Override
        public boolean hasNext() {
            return curr != sentinel;
        }

        @Override
        public E next() {
            if(!hasNext()) throw new NoSuchElementException();
            E val = curr.object;
            curr = curr.next;
            return val;
        }
    }

    private class InnerListIterator implements ListIterator<E>{
        Element curr;
        public InnerListIterator() {
            curr = sentinel.next;
        }
        @Override
        public boolean hasNext() {
            return curr != sentinel;
        }

        @Override
        public E next() {
            if(!hasNext()) throw new NoSuchElementException();
            E val = curr.object;
            curr = curr.next;
            return val;
        }

        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException();
        }
        @Override
        public boolean hasPrevious() {
            return curr.prev != sentinel;
        }
        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }
        @Override
        public E previous() {
            if(!hasPrevious()) throw new NoSuchElementException();
            curr = curr.prev;
            return curr.object;
        }
        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        @Override
        public void set(E arg0) {
            throw new UnsupportedOperationException();
        }
    }
    public TwoWayCycledOrderedListWithSentinel() {
        sentinel = new Element(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    //@SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        if(this.isEmpty()){
            sentinel.next = new Element(e, sentinel, sentinel);
            sentinel.prev = sentinel.next;
            size++;
            return true;
        }
        Element curr = sentinel.next;
        while (curr != sentinel && ((Comparable<E>)curr.object).compareTo(e) <= 0){
            curr = curr.next;
        }
        Element newElem = new Element(e, curr, curr.prev);
        curr.prev.next = newElem;
        curr.prev = newElem;
        size++;

        return true;
    }

    private Element getElement(int index) {
        if(index >= size || index < 0) throw new NoSuchElementException();
        int i = -1;
        Element curr = sentinel;
        while(i < index){
            curr = curr.next;
            i++;
        }


        return curr;
    }

    private Element getElement(E obj) {
        if(!contains(obj)) throw new NoSuchElementException();

        Element curr = sentinel.next;
        while(curr != sentinel){
            if(curr.object.equals(obj)) return curr;
            curr = curr.next;
        }

        throw new NoSuchElementException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void clear() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        Element curr = sentinel.next;
        while(curr != sentinel){
            if(curr.object.equals(element)) return true;
            curr = curr.next;
        }

        return false;
    }

    @Override
    public E get(int index) {
        return getElement(index).object;
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(E element) {
        int i = 0;
        Element curr = sentinel.next;
        while(curr != sentinel){
            if(curr.object.equals(element)) return i;
            curr = curr.next;
            i++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel && sentinel.prev == sentinel;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new InnerListIterator();
    }

    @Override
    public E remove(int index) {
        if(index < 0 || index > size - 1) throw new NoSuchElementException();
        Element elemToRemove = getElement(index);

        elemToRemove.next.prev = elemToRemove.prev;
        elemToRemove.prev.next = elemToRemove.next;
        size--;
        return elemToRemove.object;
    }

    @Override
    public boolean remove(E e) {
        try {
            int indexToRemove = indexOf(e);
            if(indexToRemove == -1) return false;
            remove(indexToRemove);
            return true;
        }
        catch (NoSuchElementException exception){
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    //@SuppressWarnings("unchecked")
    public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
        if (other == null || other == this || other.isEmpty()) return;
        if (this.isEmpty()){
            this.sentinel.next = other.sentinel.next;
            this.sentinel.prev = other.sentinel.prev;
            sentinel.next.prev = sentinel;
            sentinel.prev.next = sentinel;
            size = other.size;
            other.clear();
            return;
        }

        Element curr = sentinel.next;
        Element currOther = other.sentinel.next;

        while (currOther != other.sentinel) {

            while (curr != sentinel && ((Comparable<E>)curr.object).compareTo(currOther.object) <= 0) {
                curr = curr.next;
            }

            Element otherTemp = currOther.next;
            currOther.next = curr;
            currOther.prev = curr.prev;
            curr.prev.next = currOther;
            curr.prev = currOther;
            size++;
            currOther = otherTemp;
        }

        other.clear();
    }

    //@SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeAll(E e) {
        Element currElem = sentinel.next;
        while (currElem != sentinel){
            if (currElem.object.equals(e)){
                currElem.prev.next = currElem.next;
                currElem.next.prev = currElem.prev;
                size--;
            }
            currElem = currElem.next;
        }
    }

    public void removeDuplicates(){
        if(isEmpty()) return;
        Element curr = sentinel.next;

        while(curr != sentinel){
            while(curr.next != sentinel && curr.object.equals(curr.next.object)){
                curr.next = curr.next.next;
                curr.next.prev = curr;
                size--;
            }
            curr = curr.next;
        }
    }

}