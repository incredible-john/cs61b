public class ArrayDeque<T> {
    private T[] arr;
    private int front;
    private int back;
    private int size;

    public ArrayDeque() {
        arr = (T[]) new Object[8];
        front = arr.length - 1;
        back = 0;
        size = 0;
    }

    private void resize(int capacity) {
        T[] newArr = (T[]) new Object[capacity];
        if (front < back) {
            System.arraycopy(arr, front + 1, newArr, 0, size);
        } else {
            int start = (front + 1) % arr.length;
            int rightSize = arr.length - 1 - front;
            int leftSize = size - rightSize;
            System.arraycopy(arr, start, newArr, 0, rightSize);
            System.arraycopy(arr, 0, newArr, rightSize, leftSize);
        }
        arr = newArr;
        front = arr.length - 1;
        back = size;
    }

    /** Resize if size > arr.length */
    private void extend() {
        resize(arr.length * 2);
    }

    /** Resize if size/arr.length < 0.25 */
    private void crop() {
        resize(arr.length / 2);
    }

    /** adjust front when front is changed */
    private void adjustFront() {
        if (front < 0) {
            front = arr.length - 1;
        }
        if (front > arr.length - 1) {
            front = 0;
        }
        if (front == back && size > 0) {
            extend();
        }
        if (front == back && size < 0) {
            front = front - 1;
            adjustFront();
            size += 1;
        }
    }

    /** adjust front when front is changed */
    private void adjustBack() {
        if (back < 0) {
            back = arr.length - 1;
        }
        if (back > arr.length - 1) {
            back = 0;
        }
        if (back == front && size > 0) {
            extend();
        }
        if (back == front && size < 0) {
            back += 1;
            adjustBack();
            size += 1;
        }
    }

    /** Add one element to the front of the ArrayDeque */
    public void addFirst(T item) {
        arr[front] = item;
        front -= 1;
        size += 1;
        adjustFront();
    }

    /** Add one element to the back of the ArrayDeque */
    public void addLast(T item) {
        arr[back] = item;
        back += 1;
        size += 1;
        adjustBack();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private int arrLength() {
        return arr.length;
    }

    public void printDeque() {
        for (int i = front + 1, j = 0; j < size; i++, j++) {
            if (i > arr.length - 1) {
                i = i % arr.length;
            }
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        int indexOfFirst = (front + 1) % arr.length;
        T first = arr[indexOfFirst];
        arr[indexOfFirst] = null;
        front += 1;
        size -= 1;
        adjustFront();
        if ((double) size / arr.length < 0.25) {
            crop();
        }
        return first;
    }

    public T removeLast() {
        int indexOfLast = Math.floorMod(back - 1, arr.length);
        T last = arr[indexOfLast];
        arr[indexOfLast] = null;
        back -= 1;
        size -= 1;
        adjustBack();
        if ((double) size / arr.length < 0.25 && arr.length > 8) {
            crop();
        }
        return last;
    }

    public T get(int index) {
        int internalIndex = (front + 1 + index) % arr.length;
        return arr[internalIndex];
    }

}
