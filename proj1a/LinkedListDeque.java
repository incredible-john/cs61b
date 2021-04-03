public class LinkedListDeque<T> {
    private TNode sentinel;
    private int size;

    private class TNode {
        private T item;
        private TNode prev;
        private TNode next;

        public TNode(T i, TNode p, TNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        TNode newTNode = new TNode(item, sentinel, sentinel.next);
        sentinel.next.prev = newTNode;
        sentinel.next = newTNode;
        size += 1;
    }

    public void addLast(T item) {
        TNode newTNode = new TNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = newTNode;
        sentinel.prev = newTNode;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        T res = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        if (size > 0) {
            size -= 1;
        }
        return res;
    }

    public T removeLast() {
        T res = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        if (size > 0) {
            size -= 1;
        }
        return res;
    }

    public T get(int index) {
        TNode p = sentinel;
        while (index + 1 != 0 && 0 <= index && index < size) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    /** Same as get, but uses recursion.*/
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        if (index == 0) {
            return sentinel.next.item;
        }
        // Save
        TNode originNext = sentinel.next;
        TNode originNextNextPrev = sentinel.next.next.prev;
        // Recursion
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        T res = getRecursive(index - 1);
        // Recover
        sentinel.next.prev = originNextNextPrev;
        sentinel.next = originNext;
        return res;
    }
}
