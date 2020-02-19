package loopbag;
import java.util.Iterator;
public interface LoopBag<E> extends Iterable<E> {

    /**
     * Adds the given item to bag LoopBag. You can assume there are no
     * duplicate entries in the test cases (You don't need to handle duplicates).
     * @param item the item to add
     */
    void insert(E item);

    /**
     * Returns the number of items in this LoopBag.
     * @return the number of items in this LoopBag
     */
    int size();

    /**
     * Returns true if this LoopBag is empty and false otherwise.
     * @return true if this LoopBag is empty; false otherwise.
     **/
    boolean isEmpty();

    /**
    * method to determine if the LoopBag contains a given item.
    * @return true if bag contains the item. false otherwise
    */
    boolean contains(E item);

    /**
    * union takes a second LoopBag, lb, and "this", your current LoopBag,
    * and alters "this" so that it contains all of the items in both LoopBags.
    *
    * Union is subject to the following additional constraints:
    *   * No duplicates allowed.  Only one of each unique entry should be in the resulting bag.
    *         Ex: [1,2,3] union [2,3,4] is [1,2,3,4]
    *   * The size of the bag must remain unchanged.
    *         Ex: if [1,2] has a capacity of 3, and union is performed with [3,4],
    *             then [2,3,4] is the resulting bag
    *   * Oldest entries will be removed first.
    *         Ex: if [1,2,3] union [2,3,4], then 1, and then 2 will be removed,
    *             then the result is [2,3,4]
    *
    * @param lb the other LoopBag to union in
    */
    void union(LoopBag<E> lb);

    /**
     * Intersects the second LoopBag, ls, with "this", your current LoopBag,
     * and alters "this" so that it contains only the items that appear in both LoopBags.
     *
     * Intersect is subject to the same constraints as union
     *
     * @param lb the other LoopBag to perform intersect on.
     */
    void intersect(LoopBag<E> lb);

    /**
     * Returns an iterator for this LoopBag.
     *
     * You only need to implement next and hasNext
     * @return an iterator for this LoopBag
     */
    Iterator<E> iterator();

    /**
     * Returns a comma separated string of bag items (check the test cases for
     * examples of the required format)
     *
     * @return String representation of bag items
     */
    String toString();
}

