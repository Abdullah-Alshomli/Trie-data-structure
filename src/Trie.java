import java.util.LinkedList;

public class Trie {

    private TrieNode root;


    // constructor
    public Trie(){
        // this '\0' for empty character
        root = new TrieNode('\0');
    }

    /* methods */

    /* check if a word is in the trie */
    public boolean contains(String s){
        TrieNode lastNode = getLastNode(s);
        return lastNode != null && lastNode.isWord;
    }

    /* check if any word in the trie start with 'p' */
    public boolean isPrefix(String p){
        return getLastNode(p) != null;
    }

    /* inserts a word in the trie */
    public void insert(String s){
        // the node that will be tracked
        TrieNode curr = root;

        // loop over all the characters
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // if the node for the character has never been made
            if (curr.children[c - 'A'] == null){          // [c - 'A'] will give the index of the character 'c'
                curr.children[c - 'A'] = new TrieNode(c);

            }
            curr = curr.children[c - 'A'];

        }
        // defining the last character as a word
        curr.isWord = true;

    }

    /* delete a word from the trie */
    public void delete(String s){
        try {
            TrieNode lastNode = getLastNode(s);

            // to check if the node have children
            int numOfNods = 0;
            for (int i = 0; i < 26; i++) {
                if (lastNode.children[i] != null){
                    numOfNods++;
                }
            }

            // if the node have children -> isWord = false
            if(numOfNods > 0){
                lastNode.isWord = false;
            }

            // if the node have no children -> "delete it"
            else {
                char c =  lastNode.c ;
                lastNode = getLastNode(s.substring(0,s.length()-1));
                lastNode.children[c - 'A'] = null;
            }

            // to loop over each character
            if(s.length() > 1){
                delete(s.substring(0,s.length()-1));
            }

        }
        catch (Exception e){
            System.out.println("Error: the trie dose not contain the word " + s);
        }

    }

    /* check if the trie does not have any node */
    public boolean isEmpty(){
        TrieNode curr = root;
        for (int i = 0; i < 26 ; i++) {
            if (curr.children[i] != null){
                return false;
            }
        }
        return true;
    }

    /* remove all the nodes in the trie */
    public void clear(){
        TrieNode curr = root;
        for (int i = 0; i < 26; i++) {
            curr.children[i] = null;
        }
    }

    /* returns all the words that start with the string 'p' */
    public String[] allWordsPrefix(String p){
        LinkedList<String> result = new LinkedList<>();
        TrieNode curr = getLastNode(p);

        // if the node is already a word
        if (curr.isWord){
            result.add(p);
        }
        // looping over all the children
        for (TrieNode child : curr.children) {
            if (child != null){
                LinkedList<String> newResult = allWordsPrefixHelper(child, p, new LinkedList<>());
                result.addAll(newResult);

            }

        }
        // making the array of words
        String[] array = new String[result.size()];
        int i = 0;
        for (String s: result) {
            array[i] = s;
            i++;
        }
        return array;
    }

    /* a helper method that will return a list of words for the child*/
    private LinkedList<String> allWordsPrefixHelper(TrieNode curr, String word, LinkedList<String> result){
        if (curr.isWord){
            result.add(word + curr.c);
        }
        for (TrieNode child: curr.children) {
            if (child != null){
                allWordsPrefixHelper(child, word + curr.c, result);
            }
            
        }
        return result;
    }

    /* return the number of nodes in the trie */
    public int size(){
        return  sizeHelper(root);

    }

    /* helper for the first size method */
    public int sizeHelper(TrieNode node){
        int theSize = 0;
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null){
                theSize++;
                theSize += sizeHelper(node.children[i]);
            }
        }
        return theSize;
    }

    /* "helper method" give the last node of a word if it exists */
    private TrieNode getLastNode(String s){
        TrieNode curr = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // checking if the character exists
            if (curr.children[c - 'A'] == null){
                return null;
            }
            curr = curr.children[c - 'A'];
        }
        return curr;

    }

}
