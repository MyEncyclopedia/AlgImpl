/**
 * https://leetcode.com/problems/implement-trie-prefix-tree/
 *
 * Implement a trie with insert, search, and startsWith methods.
 *
 * Note:
 * You may assume that all inputs are consist of lowercase letters a-z.
 */

import java.util.HashMap;

class TrieNode {

    HashMap<Character, TrieNode> children;
    boolean isEnd = false;

    // Initialize your data structure here.
    public TrieNode() {
        children = new HashMap<Character, TrieNode>();
    }
}

public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        TrieNode node = root;
        while (!word.isEmpty()) {
            char c = word.charAt(0);
            if (!node.children.containsKey(c)) {
                node.children.put(c, new TrieNode());
            }
            node = node.children.get(c);
            word = word.substring(1);
        }
        node.isEnd = true;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        return searchString(word, true);
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        return searchString(prefix, false);
    }

    private boolean searchString(String word, boolean ends) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        TrieNode node = root;
        while (!word.isEmpty()) {
            char c = word.charAt(0);
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
            word = word.substring(1);
        }
        return ends ? node.isEnd : true;
    }

}
// Your Trie object will be instantiated and called as such:
// Trie trie = new Trie();
// trie.insert("somestring");
// trie.search("key");
