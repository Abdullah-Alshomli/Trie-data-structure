public class TrieNode {
    // data stored in a node
    public char c;
    public boolean isWord;
    public TrieNode[] children;

    // constructor
    public TrieNode(char c){
        this.c = c;
        isWord = false;

        // 26 for A to Z capital letters
        children = new TrieNode[26];  // A index is 0 , Z index is 25

    }
}
