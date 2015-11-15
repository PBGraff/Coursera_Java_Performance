package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		this.size = 0;
		this.head = new LLNode<E>(null);
		this.tail = new LLNode<E>(null, this.head);
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		if (element == null) {
			throw new NullPointerException();
		}
		new LLNode<E>(element, this.tail.prev);
		this.size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> current = this.head.next;
		for (int i=0; i<index; i++) {
			current = current.next;
		}
		return current.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		if (index < 0 || index > this.size) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		LLNode<E> prev = this.head;
		for (int i=0; i<index; i++) {
			prev = prev.next;
		}
		new LLNode<E>(element, prev);
		this.size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> item = this.head.next;
		for (int i=0; i<index; i++) {
			item = item.next;
		}
		item.prev.next = item.next;
		item.next.prev = item.prev;
		this.size--;
		return item.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		LLNode<E> current = this.head.next;
		for (int i=0; i<index; i++) {
			current = current.next;
		}
		E old = current.data;
		current.data = element;
		return old;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E e, LLNode<E> p)
	{
		this.data = e;
		this.prev = p;
		this.next = p.next;
		if (this.next != null) {
			this.next.prev = this;
		}
		p.next = this;
	}

}
