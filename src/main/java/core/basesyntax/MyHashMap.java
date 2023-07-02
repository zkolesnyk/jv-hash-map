package core.basesyntax;


import java.util.Objects;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private double threshold;
    private Node<K, V>[] table;
    private int size;

    public MyHashMap() {
        table = new Node[INITIAL_CAPACITY];
        threshold = INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR;
    }


    @Override
    public void put(K key, V value) {
        int index = hash(key);
        Node<K, V> newNode = new Node<>(key, value);
        Node<K, V> node = table[index];
        while (node != null) {
            if (Objects.equals(node.key, newNode.key)) {
                node.value = newNode.value;
                return;
            }
            node = node.next;
        }
        newNode.next = table[index];
        table[index] = newNode;
        size++;

//        if (size > threshold) {
//            resize();
//        }
    }

    @Override
    public V getValue(K key) {
        int index = hash(key);
        Node<K, V> node = table[index];
        while (node != null) {
            if (Objects.equals(node.key, key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private int hash(K key) {
        return key == null ? 0 : Math.abs(key.hashCode() % table.length);
    }

    private void resize() {
        int newCapacity = table.length << 1;
        Node<K, V>[] oldTable = table;
        table = new Node[newCapacity];

        for (Node<K, V> node : oldTable) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }

    static class Node<K, V> {
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            hash = key == null ? 0 : key.hashCode();
        }
        final int hash;
        final K key;

        V value;
        Node<K, V> next;
    }
}
