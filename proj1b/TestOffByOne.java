import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('j', 'k'));
        assertTrue(offByOne.equalChars('z', 'y'));
        assertTrue(offByOne.equalChars('p', 'q'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('a', 'i'));
        assertFalse(offByOne.equalChars('n', 'h'));
    }
}
