import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("A"));
        assertTrue(palindrome.isPalindrome("NN"));
        assertTrue(palindrome.isPalindrome("abiba"));
        assertFalse(palindrome.isPalindrome("catbac"));
    }

    @Test
    public void testNewIsPalindrome() {
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("A", cc));
        assertFalse(palindrome.isPalindrome("NN", cc));
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertFalse(palindrome.isPalindrome("catbac", cc));
    }
}
