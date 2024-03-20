import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DoublyLinkedList<Integer> ordenados = new DoublyLinkedList();

        ordenados.insertOrder(1);
        ordenados.insertOrder(5);
        ordenados.insertOrder(4);
        ordenados.insertOrder(2);
        ordenados.insertOrder(7);
        ordenados.insertOrder(10);


        ordenados.insert(3,9);
        ordenados.add(4);
        ordenados.insert(11);


        // ordenados.insertOrder("C");
        // ordenados.insertOrder("A");
        // ordenados.insertOrder("E");
        // ordenados.insertOrder("B");
        // ordenados.insertOrder("F");

        System.out.println(ordenados);
        System.out.println(ordenados.reverseString());

    }
}

// Exceptions
class EmptyListException extends RuntimeException {
    public EmptyListException(String errorMessage) {
        super(errorMessage);
    }
}

// TAD - List
interface List<E> {
    int size();

    void add(E value);

    void insert(E value);

    void insert(int index, E value) throws IndexOutOfBoundsException;

    E removeLast() throws EmptyListException;

    E removeFirst() throws EmptyListException;

    E removeByIndex(int index) throws IndexOutOfBoundsException, EmptyListException;

    boolean isEmpty();

    E get(int index) throws IndexOutOfBoundsException;

    void set(int index, E value) throws IndexOutOfBoundsException;
}

class DoublyLinkedList<E> implements List<E> {

    private class Node {
        E value;
        Node next;
        Node previous;

        public Node(E value) {
            this.value = value;
            // next = null;
        }
    }

    private int size;
    private Node head;
    private Node tail;

    public DoublyLinkedList() {
    }

    public DoublyLinkedList(E value) {
        add(value);
    }

    // Adiciona um novo elemento ao final da lista.
    @Override
    public void add(E value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            head = newNode;
        } else {
            newNode.previous = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    // Retorna o elemento na posição especificada na lista.
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new EmptyListException("Linked List is Empty!");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Illegal index " + index + ". Availabe indexes are [0 - " + (size - 1) + "]");
        }

        return getNode(index).value;
    }

    // Insere um novo elemento no início da lista.
    @Override
    public void insert(E value) {
        Node newNode = new Node(value);

        if (isEmpty()) {
            // head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.previous = newNode;
        }
        head = newNode;

        size++;
    }

    // Retorna o nó na posição especificada.
    private Node getNode(int index) {
        Node auxNode = head;
        for (int i = 0; i < index; i++) {
            auxNode = auxNode.next;
        }
        return auxNode;
    }

    // Insere um elemento em uma posição específica na lista.
    @Override
    public void insert(int index, E value) {

        if (index <= 0) {
            insert(value);
        } else if (index >= size) {
            add(value);
        } else {
            Node newNode = new Node(value);
            Node auxNode = getNode(index);
            newNode.next = auxNode;
            newNode.previous = auxNode.previous;
            auxNode.previous.next = newNode;
            auxNode.previous = newNode;

            size++;

        }

    }

    // Insere um elemento de forma ordenada, assumindo que os elementos da lista implementam Comparable.
    public void insertOrder(E value) {
        if (!(value instanceof Comparable)) {
            throw new IllegalArgumentException("Value must be Comparable");
        }

        Node newNode = new Node(value);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            size++;
            return;
        }

        Node current = head;
        // Como a lista é genérica, é seguro assumir que o valor é instância de Comparable
        Comparable<E> comparableValue = (Comparable<E>) value;

        // Encontre a posição de inserção
        while (current != null && comparableValue.compareTo(current.value) > 0) {
            current = current.next;
        }

        if (current == null) {
            // Inserir no final da lista
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        } else if (current.previous == null) {
            // Inserir no começo da lista
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        } else {
            // Inserir no meio da lista
            newNode.next = current;
            newNode.previous = current.previous;
            current.previous.next = newNode;
            current.previous = newNode;
        }

        size++;
    }

    // Verifica se a lista está vazia.
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Remove e retorna o elemento na posição especificada na lista.
    @Override
    public E removeByIndex(int index) throws IndexOutOfBoundsException, EmptyListException {
        if (isEmpty()) {
            throw new EmptyListException("Doubly LinkedList List is Empty!");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Illegal index " + index + ". Availabe indexes are [0 - " + (size - 1) + "]");
        }
        E value = null;
        if (index == 0) {
            value = removeFirst();
        } else if (index == size - 1) {
            value = removeLast();
        } else {
            Node auxNode = getNode(index);

            auxNode.previous.next = auxNode.next;
            auxNode.next.previous = auxNode.previous;
            // auxNode.next = null;
            // auxNode.previous = null;
            size--;
        }

        return value;
    }

    // Remove e retorna o primeiro elemento da lista.
    @Override
    public E removeFirst() throws EmptyListException {
        if (isEmpty()) {
            throw new EmptyListException("Doubly LinkedList List is Empty!");
        }
        E value = head.value;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            // head.previous.next = null;
            head.previous = null;

        }
        size--;
        return value;
    }

    // Remove e retorna o último elemento da lista.
    @Override
    public E removeLast() throws EmptyListException {
        if (isEmpty()) {
            throw new EmptyListException("DoublyLinkedList List is Empty!");
        }
        E value = tail.value;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.previous;
            // tail.next.previous = null;
            tail.next = null;
        }
        size--;
        return value;
    }

    // Substitui o elemento na posição especificada na lista pelo elemento especificado.
    @Override
    public void set(int index, E value) throws IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new EmptyListException("DoublyLinkedList List is Empty!");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Illegal index " + index + ". Availabe indexes are [0 - " + (size - 1) + "]");
        }

        getNode(index).value = value;
    }

    // Retorna o número de elementos na lista.
    @Override
    public int size() {
        return size;
    }

    // Retorna uma representação em String da lista.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node auxNode = head;
        while (auxNode != null) {
            sb.append(auxNode.value);
            if (auxNode.next != null) {
                sb.append(", ");
            }
            auxNode = auxNode.next;
        }
        return sb.append("]").toString();
    }

    // Retorna uma representação em String da lista em ordem inversa.
    public String reverseString() {
        StringBuilder sb = new StringBuilder("[");
        Node auxNode = tail;
        while (auxNode != null) {
            sb.append(auxNode.value);
            if (auxNode.previous != null) {
                sb.append(", ");
            }
            auxNode = auxNode.previous;
        }
        return sb.append("]").toString();
    }

}