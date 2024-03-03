package org.prokopovich.custom_structures;

public class CustomHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 10;
    private HashMapNode<K, V> data[];
    private int size;

    public CustomHashMap() {
        this.data = new HashMapNode[INITIAL_CAPACITY];
        this.size = 0;
    }

    class HashMapNode<K, V> {
        K key;
        V value;
        HashMapNode<K, V> next;

        public HashMapNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null is forbidden key");
        }

        int index = getIndex(key);
        if (data[index] == null) { //если бакет пустой
            data[index] = new HashMapNode<>(key, value);
            size++;
            return;
        }

        /**
         * Этот блок нужен для того, чтобы, если в бакете связанный список > 1 элемента и мы добавляем копию ключа ПЕРВОГО элемента
         * пример: в бакете с индексом 2 лежит список А(1)->Б(2) и мы добавляем в мапу A(100), то тогда список станет А(100)->Б(2)
         * p.s. проверка выглядит странной, это временное решение
         */
        HashMapNode<K, V> iterator = data[index];

        if (iterator.next == null || iterator.next != null) {
            if (iterator.key.hashCode() == key.hashCode()) {
                if (iterator.key.equals(key)) {
                    iterator.value = value;
                    return;
                }
            }
        }
        /**
         * Eсли бакет не пустой, то значит в нем уже есть
         * связанный список как минимум из одного элемента
         * и iterator бежит по этому списку с первого его элемента
         * до именно последнего (цикл while) и подвязывает к
         * последнему элементу списка добавляемый нами элемент
         */
        while (iterator.next != null) {
            if (iterator.next.key.hashCode() == key.hashCode()) {
                if (iterator.next.key.equals(key)) {
                    iterator.next.value = value;
                    size++;
                    return;
                }
            }
            iterator = iterator.next;
        }
        iterator.next = new HashMapNode<>(key, value);
    }

    private int getIndex(K key) {
        int index = Math.abs(key.hashCode() % data.length);
        return index;
    }

    public V getValue(K key) {
        int index = getIndex(key);
        if (data[index] == null) {
            return null;
        }
        if (data[index].key.equals(key)) {
            return data[index].value;
        }
        HashMapNode<K, V> iterator = data[index];
        while (iterator != null && !iterator.key.equals(key)) {
            iterator = iterator.next;
        }
        if (iterator != null) {
            return iterator.value;
        } else {
            return null;
        }
    }

    public void remove(K key) {
        if(key!=null){
            int index = getIndex(key);
            if (data[index] == null) {
                System.out.println("No suck a key");
                return;
            }
            if (data[index].key.equals(key)&&data[index].next==null) {//в бакете был список из 1 элемента
                data[index]=null;
            }
            HashMapNode<K,V> first=data[index];
            HashMapNode<K,V> previous=null;
            HashMapNode<K,V> iterator=data[index];
            while (iterator != null && !iterator.key.equals(key)) {
                previous=iterator;
                iterator = iterator.next;
            }
            if (previous!=null){
                previous.next=iterator.next;
                return;
            }
            first=iterator;
        }
    }
}
