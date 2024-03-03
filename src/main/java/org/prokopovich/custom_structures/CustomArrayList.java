package org.prokopovich.custom_structures;


import java.util.*;


public class CustomArrayList<T> implements Iterable {


    public static final int INTEGER_UPPER_BORDER = Integer.MAX_VALUE - 1;
    private T[] elementsArray;
    private int size;// указатель на место добавления элемента и также показывает количество элементов в списке
    private int capacity;
    private final int DEFAULT_CAPACITY = 11;

    public CustomArrayList() {
        elementsArray = (T[]) new Object[DEFAULT_CAPACITY];
        this.capacity = elementsArray.length;
        this.size = 0;
    }

    public void add(T value) {
        checkSize();
        elementsArray[size] = value;
        size += 1;
    }

    private void checkSize() {
        if (size >= capacity) {
            boolean resizeResult = resize();
            if (resizeResult == false) {
                throw new RuntimeException("Невозможно добавить элемент");
            }
        }
    }

    private boolean resize() {
        if (capacity >= INTEGER_UPPER_BORDER) {
            return false;
        }
        long newCapacityLong = (capacity * 3) / 2 + 1;
        int newCapacityInt = newCapacityLong < INTEGER_UPPER_BORDER ? (int) newCapacityLong : INTEGER_UPPER_BORDER;
        Object[] newElementsArray = new Object[newCapacityInt];
        System.arraycopy(elementsArray, 0, newElementsArray, 0, capacity);
        elementsArray = (T[]) newElementsArray;// пресечение возможности влияния изменений объектов в старом массиве на содержимое нового массива, т.к. копировались ссылки на объекты, а не сами объекты
        capacity = newCapacityInt;
        return true;
    }

    public void addByIndex(int index, T value) {
        checkIndex(index);
        checkSize();
        System.arraycopy(elementsArray, index, elementsArray, index + 1, size - index);
        elementsArray[index] = value;
        size += 1;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void deleteByIndex(int index) {
        checkIndex(index);
        //1 что копируем, 2 с какой позиции, 3 куда копируем, 4 с какой позиции, 5 сколько
        System.arraycopy(elementsArray, index + 1, elementsArray, index, size - index);
        size -= 1;
    }

    public int size() {
        return size;
    }

    public void clear() {
        elementsArray = (T[]) new Object[0];
        capacity = elementsArray.length;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("[");
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                buffer.append(elementsArray[i]).append(", ");
            } else {
                buffer.append(elementsArray[i]);
            }
        }
        buffer.append("]");
        return buffer.toString();
    }

    public T get(int index) {
        checkIndex(index);
        return elementsArray[index];
    }

    public boolean contains(T o) {
        int i = 0;
        while (i < size) {
            if (o == null) {
                if (elementsArray[i] == null) {
                    return true;
                }
            } else {
                if (o.equals(elementsArray[i])) {
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof CustomArrayList<?> == false) return false;
        CustomArrayList<T> otherList = (CustomArrayList<T>) o;
        if (this.size() != otherList.size()) return false;

        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(otherList.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 3;
        for (int i = 0; i < size(); i++) {
            result = 7 * result + get(i).hashCode();
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomArrayListIterator();
    }

    private class CustomArrayListIterator implements ListIterator<T> {
        private int currentIndex;

        public CustomArrayListIterator() {
            this.currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T element = (T) elementsArray[currentIndex];
            currentIndex++;
            return element;
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            T element = (T) elementsArray[currentIndex - 1];
            currentIndex--;
            return element;
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return currentIndex-1;
        }

        @Override
        public void remove() {
            deleteByIndex(currentIndex);
        }

        @Override
        public void set(T t) {
            addByIndex(currentIndex,t);
        }

        @Override
        public void add(T t) {
            add(t);
        }
    }
}





