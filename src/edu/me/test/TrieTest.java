import edu.me.datastructure.Trie;
import edu.me.datastructure.model.node.TrieNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {
    private final Trie testTrie = new Trie(new TrieNode('*'));

    @Test
    void insert() {
        this.testTrie.insert("airplane");
        this.testTrie.insert("air");
        this.testTrie.insert("amen");
        System.out.println("");
    }

//    @Test
//    void search() {
//        this.testTrie.insert("cut");
//        String word = this.testTrie.search("cut");
//        System.out.println(word);
//    }

    @Test
    void autoComplete() {
        this.testTrie.insert("cot");
        this.testTrie.insert("cut");
        this.testTrie.insert("cat");
        List<String> related = this.testTrie.autoComplete("c");
        System.out.println(related.toString());
    }
}