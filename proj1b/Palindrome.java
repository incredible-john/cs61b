public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    private boolean isPalindromeHelper(Deque<Character> wd) {
        if (wd.size() == 0 || wd.size() == 1) {
            return true;
        } else if (wd.removeFirst() == wd.removeLast()) {
            return isPalindromeHelper(wd);
        } else {
            return false;
        }
    }

    private boolean isPalindromeHelper2(Deque<Character> wd, CharacterComparator cc) {
        if (wd.size() == 0 || wd.size() == 1) {
            return true;
        } else if (cc.equalChars(wd.removeFirst(), wd.removeLast())) {
            return isPalindromeHelper2(wd, cc);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word) {
        return isPalindromeHelper(wordToDeque(word));
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindromeHelper2(wordToDeque(word), cc);
    }

}
