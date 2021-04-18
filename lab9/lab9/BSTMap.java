package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (key.compareTo(p.key) == 0)  {
            return p.value;
        } else if (key.compareTo(p.key) > 0) {
            return getHelper(key, p.right);
        } else {
            return getHelper(key, p.left);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        } else if (key.compareTo(p.key) == 0) {
            p.value = value;
        } else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.left = putHelper(key, value, p.left);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    private int height(Node p) {
        if (p == null) {
            return -1;
        }
        return 1 + Math.max(height(p.left), height(p.right));
    }

    private void levelHelper(Set<K> keys, int level, Node p) {
        if (p == null) {
            return;
        } else if (level == 0) {
            keys.add(p.key);
            System.out.println(p.key);
        } else {
            levelHelper(keys, level - 1, p.left);
            levelHelper(keys, level - 1, p.right);
        }
    }

    private void keySetHelper(Set<K> keys, Node p) {
        // pre-order traversal, in-order traversal, post-order traversal
//        if (p == null) {
//            return;
//        }
//        keys.add(p.key);
//        keySetHelper(keys, p.left);
//        keySetHelper(keys, p.right);

        // level traversal
        int h = height(root);
        for (int i = 0; i < h + 1; i += 1) {
            levelHelper(keys, i, root);
        }
    }
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        keySetHelper(keys, root);
        return keys;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    private Node largest(Node p) {
        if (p == null) {
            return null;
        } else if (p.right != null) {
            return largest(p.right);
        }
        return p;
    }

    private Node smallest(Node p) {
        if (p == null) {
            return null;
        } else if (p.left != null) {
            return smallest(p.left);
        }
        return p;
    }

    private Node parent(Node p, Node root) {
        if (root == null) {
            return null;
        }
        if (root.left == p || root.right == p || p == root) {
            return root;
        }
        if (p.key.compareTo(root.key) > 0) {
            return parent(p, root.right);
        } else {
            return parent(p, root.left);
        }
    }

    private void changeBossLeft(Node curBoss, Node newBoss) {
        // change boss
        curBoss.key = newBoss.key;
        curBoss.value = newBoss.value;
        // remove newBoss's old position
        Node parentOfNewBoss = parent(newBoss, root);
        if (parentOfNewBoss.left == newBoss) {
            parentOfNewBoss.left = null;
        } else if (newBoss.left == null && newBoss.right == null) {
            parentOfNewBoss.right = null;
        } else if (newBoss.left != null && newBoss.right == null) {
            newBoss.key = newBoss.left.key;
            newBoss.value = newBoss.left.value;
            newBoss.left = null;
        }
    }

    private void changeBossRight(Node curBoss, Node newBoss) {
        // change boss
        curBoss.key = newBoss.key;
        curBoss.value = newBoss.value;
        // remove newBoss's old position
        Node parentOfNewBoss = parent(newBoss, root);
        if (parentOfNewBoss.right == newBoss) {
            parentOfNewBoss.right = null;
        } else if (newBoss.right == null && newBoss.left == null) {
            parentOfNewBoss.left = null;
        } else if (newBoss.left == null) {
            newBoss.key = newBoss.right.key;
            newBoss.value = newBoss.right.value;
            newBoss.right = null;
        }
    }

    private V removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (p.key.compareTo(key) == 0) {
            Node leftLargest = largest(p.left);
            Node rightSmallest = smallest(p.right);
            V oldBossValue = p.value;
            if (leftLargest != null) {
                changeBossLeft(p, leftLargest);
            } else if (rightSmallest != null) {
                changeBossRight(p, rightSmallest);
            } else {
                Node parentOfP = parent(p, root);
                if (parentOfP.left == p) {
                    parentOfP.left = null;
                } else if (parentOfP.right == p) {
                    parentOfP.right = null;
                } else {
                    root = null;
                }
            }
            return oldBossValue;
        } else if (key.compareTo(p.key) > 0) {
            return removeHelper(key, p.right);
        } else {
            return removeHelper(key, p.left);
        }
    }

    @Override
    public V remove(K key) {
        return removeHelper(key, root);
    }

    private V removeHelper2(K key, V value, Node p) {
        if (p == null) {
            return null;
        } else if (p.key.compareTo(key) == 0 && p.value == value) {
            Node leftLargest = largest(p.left);
            Node rightSmallest = smallest(p.right);
            V oldBossValue = p.value;
            if (leftLargest != null) {
                changeBossLeft(p, leftLargest);
            } else if (rightSmallest != null) {
                changeBossRight(p, rightSmallest);
            } else {
                Node parentOfP = parent(p, root);
                if (parentOfP.left == p) {
                    parentOfP.left = null;
                } else if (parentOfP.right == p) {
                    parentOfP.right = null;
                } else {
                    root = null;
                }
            }
            return oldBossValue;
        } else if (key.compareTo(p.key) > 0) {
            return removeHelper(key, p.right);
        } else {
            return removeHelper(key, p.left);
        }
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        return removeHelper2(key, value, root);
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

//    public static void main(String[] args) {
//        BSTMap<String, Integer> bstmap = new BSTMap<>();
//        bstmap.put("d", 5);
//        bstmap.put("b", 10);
//        bstmap.put("f", 22);
//        bstmap.put("a", 77);
//        bstmap.put("c", 88);
//        bstmap.put("e", 14);
//        bstmap.put("g", 15);
//        bstmap.remove("f");
//        bstmap.remove("d");
//        bstmap.remove("b");
//        bstmap.remove("e");
//        bstmap.remove("a");
//        bstmap.remove("c");
//        bstmap.remove("z");
//        bstmap.remove("g");
//        System.out.println(bstmap.keySet().toString());
//    }
}
