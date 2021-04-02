public class LinkedListDequeLauncher {
    public static void main(String[] args) {
        // Testing Constructor and adding functions
        LinkedListDeque<Integer> newDeque = new LinkedListDeque<>();
//        newDeque.addFirst(1);
//        newDeque.addLast(2);
//        newDeque.addLast(3);
        // Testing isEmpty() and printing function
        System.out.println("is empty? " + newDeque.isEmpty());
        System.out.print("Whole deque: ");
        newDeque.printDeque();
        // Testing remove() and get()
        newDeque.removeFirst();
        newDeque.removeLast();
        newDeque.printDeque();
        newDeque.addLast(5);
        newDeque.addLast(7);
        System.out.println("size:" + newDeque.size() + " 1st item: " + newDeque.get(1));
        System.out.println("1st item recursive: " + newDeque.getRecursive(2));
    }
}
