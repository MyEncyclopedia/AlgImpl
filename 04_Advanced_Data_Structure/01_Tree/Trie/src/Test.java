
public class Test {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("somestring");
        trie.insert("st");
        System.out.println(trie.search("key"));
        System.out.println(trie.search("some"));
        System.out.println(trie.startsWith("som"));
    }

}
