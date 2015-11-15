package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		if (sourceText.length() == 0) {
			return;
		}
		
		String[] words = sourceText.split("[ ]+");
		
		// add first word as next of empty
		ListNode start = new ListNode(starter);
		start.addNextWord(words[0]);
		wordList.add(start);
		
		// loop over list of words
		for (int i = 0; i < words.length-1; i++) {
			String word = words[i];
			String nextword = words[i+1];
			int index = this.find(wordList, word);
			if (index == -1) {
				// list doesn't contain word already
				ListNode newnode = new ListNode(word);
				newnode.addNextWord(nextword);
				wordList.add(newnode);
			} else {
				// list has word at index
				wordList.get(index).addNextWord(nextword);
			}
		}
		
		// add last word with next as empty
		int index = this.find(wordList, words[words.length-1]);
		if (index == -1) {
			// list doesn't contain word already
			ListNode newnode = new ListNode(words[words.length-1]);
			newnode.addNextWord("");
			wordList.add(newnode);
		} else {
			// list has word at index
			wordList.get(index).addNextWord("");
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    if (wordList.size() == 0) {
	    	return "";
	    }
		
		String starter = "";
	    String output = "";
	    
	    for (int count = 0; count < numWords; ) {
	    	int index = this.find(wordList, starter);
	    	if (index == -1) {
	    		index = this.find(wordList, "");
	    	}
	    	String next = wordList.get(index).getRandomNextWord(rnGenerator);
	    	output += next+" ";
	    	starter = next;
	    	if (!next.equals("")) {
	    		count++;
	    	}
	    }
	    
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		while (wordList.size() > 0) {
			wordList.remove(0);
		}
		this.starter = "";
		this.train(sourceText);
	}
	
	private int find(List<ListNode> wordList, String word) {
		for (int i = 0; i < wordList.size(); i++) {
			if (wordList.get(i).getWord().equals(word)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		return nextWords.get(generator.nextInt(nextWords.size()));
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


