import edu.me.datastructure.Trie;
import edu.me.datastructure.model.node.TrieNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {
    private final Trie testTrie = new Trie(new TrieNode('*'));

    @Test
    void insert() {
        this.testTrie.insert("cut");
        System.out.println("");
    }

    @Test
    void search() {
        this.testTrie.insert("cut");
        String word = this.testTrie.search("cut");
        System.out.println(word);
    }
}