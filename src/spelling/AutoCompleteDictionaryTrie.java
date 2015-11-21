package spelling;

import java.util.List;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    if (this.isWord(word)) {
	    	return false;
	    } else {
	    	recurseTrieAdd(word.toLowerCase(), -1, root);
	    	this.size++;
	    	return true;
	    }
	}
	
	private void recurseTrieAdd(String s, int i, TrieNode node) {
		if (i == s.length()-1) {
			node.setEndsWord(true);
		} else {
			if (node.getValidNextCharacters().contains(s.charAt(i+1))) {
				recurseTrieAdd(s, i+1, node.getChild(s.charAt(i+1)));
			} else {
				recurseTrieAdd(s, i+1, node.insert(s.charAt(i+1)));
			}
		}
	}


	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    TrieNode result = recurseTrieFind(s.toLowerCase(), -1, root);
		if (result == null) {
			return false;
		} else {
			return result.endsWord();
		}
	}

	private TrieNode recurseTrieFind(String s, int i, TrieNode node) {
		if (i == s.length()-1 || node == null) {
			return node;
		} else {
			if (node.getValidNextCharacters().contains(s.charAt(i+1))) {
				return recurseTrieFind(s, i+1, node.getChild(s.charAt(i+1)));
			} else {
				return null;
			}
		}
	}


	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
    	 List<String> completions = new LinkedList<String>();
    	 
    	 TrieNode stem = recurseTrieFind(prefix.toLowerCase(), -1, root);
    	 
    	 if (stem != null) {
    		 // breadth-first search
    		 List<TrieNode> q = new LinkedList<TrieNode>();
    		 q.add(stem);
    		 // stop if empty queue or enough completions obtained
    		 while (!q.isEmpty() && completions.size() < numCompletions) {
    			 // remove first in queue
    			 TrieNode node = q.remove(0);
    			 // add to completions if it's a word
    			 if (node.endsWord()) {
    				 completions.add(node.getText());
    			 }
    			 // add all of its children to the queue
    			 for (Character c : node.getValidNextCharacters()) {
    				 q.add(node.getChild(c));
    			 }
    		 }
    	 }
         
    	 return completions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}