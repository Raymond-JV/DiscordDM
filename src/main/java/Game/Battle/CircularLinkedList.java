package Game.Battle;

import java.util.Iterator;

import java.util.NoSuchElementException;

public class CircularLinkedList<T>  {

    private Node<T> front;
    private Node<T> rear;
    private int size = 0;

    public CircularLinkedList()
    {}

    public void addFront(T data)
    {
        if (front == null) {
            Node<T> node = new Node<>(data);
            node.next = node;
            node.previous = node;
            front = node;
            rear  = node;
        }
        else
        {
            Node<T> node = new Node<>(data);
            node.next = front;
            node.previous = rear;
            rear.next = node;
            front.previous = node;
            front = node;
        }
        size++;
    }

    public void addRear(T data)
    {
        if (front == null)
            addFront(data);
        else
        {
            Node<T> node = new Node<>(data);
            node.previous = rear;
            node.next = front;
            front.previous = node;
            rear.next = node;
            rear = node;
            size++;
        }
    }

    public void remove(T data)
    {
        Iterator<T> bookmark = new CircularIterator();

          while (bookmark.hasNext()) {
              if (bookmark.next() == data)
              {
                    bookmark.remove();   //iterator decrements size
                    return;
              }
          }
    }

    public int size()
    {
        return size;
    }

    public T getFront()
    {
        return (front != null) ? front.data : null;
    }

    public T getRear()
    {
        return (rear != null) ? rear.data : null;
    }


    public Iterator<T> iterator()
    {
        return new CircularIterator();
    }

    public void display()
    {
        Node<T> current = front;

        while (current != null)
        {
            System.out.println(current.data);
            current = current.next;
            if (current == front)
                break;
        }
    }

    private class Node<T> {

        Node<T> next;
        Node<T> previous;
        T data;

        private Node(T data) {
            this.data = data;
        }
    }

   private class CircularIterator implements Iterator<T>
    {
        private Node<T> current;

        @Override
        public boolean hasNext() {
            return front != null;
        }

        @Override
        public T next() {
            if (front == null)
                throw new NoSuchElementException("Error: CircularIterator tried to return a null value.");

            if (current == null)
                current = front;
            else
                current = current.next;
            return current.data;
        }

        @Override
        public void remove() {

            if (front == null)
                throw new NoSuchElementException();

            if ((front == rear) && (front.data == current.data)) {
                    front = null;
                    rear = null;
                    size--;
                    return;
                }
            if (front.data == current.data)
                front = front.next;
            if (rear.data == current.data)
                rear = rear.previous;

            current.previous.next = current.next;
            current.next.previous = current.previous;
            size--;
        }
    }
}
