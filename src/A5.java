import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.List;

/** 
 * COMP 2503 Winter 2018 Assignment 1 
 * 
 * This program reads a text file and compiles a list of the 
 * words together with how many each word appears. 
 *
 * Words from a standard list of stop words are not included.
*/
public class A5
{

	private ArrayList<Word> fill = new ArrayList<Word>();
	private HashMap<Integer, Word> words;
	private Iterator<HashMap<Integer, Word>> itr;
	
	private String[] stopwords = { "a", "about", "all", "am", "an", 
			"and", "any", "are", "as", "at", "be", "been", "but", "by", "can", 
			"cannot", "could", "did", "do", "does", "else", "for", "from", 
			"get", "got", "had", "has", "have", "he", "her", "hers", "him", 
			"his", "how", "i", "if", "in", "into", "is", "it", "its", "like", 
			"more", "me", "my", "no", "now", "not", "of", "on", "one", 
			"or", "our", "out", "said", "say", "says", "she", "so", "some",
			"than", "that", "the", "their", "them", "then", "there", "these", 
			"they", "this", "to", "too", "us", "upon", "was", "we", "were", 
			"what", "with", "when", "where", "which", "while", "who", 
			"whom", "why", 
         "will", "you", "your"};

	private int totalwordcount = 0;

	private int stopwordcount = 0;

	private Scanner inp = new Scanner( System.in);

   public static void main(String[ ] args) 
   {	
      A5 a5 = new A5();
      a5.run();
   }

   /**
    * Check if the word given is in the stopword list.
    * @param w a word 
    * @return true if w is a stopword, false otherwise
    */
   private boolean stopword( String w) 
   {
      boolean found = false;
      int i = 0;
      while (i < stopwords.length && !found) 
      {
         if ( w.equals( stopwords[i])) 
            found = true;
         i++;
      }
      return found;
   }

   /** 
   List the first n words in the list of words.
   @param n the number of words to list
   */
   private void printWords( int n) 
   {
      int i = 0;
      while ( i < words.size() && i < n) 
      {
         System.out.println( words.get(i));
         i++;
      }
   }


   private void printResults() 
   {
       System.out.println( "Total Words: " + totalwordcount);
       System.out.println( "Unique Words: " + words.size()); 
       System.out.println( "Stop Words: " + stopwordcount);
       System.out.println();
       System.out.println( "20 Most Frequent");
       //Collections.sort( words, Word.CompFreqDesc); 
       printWords(20); 
       System.out.println();
       System.out.println( "20 Least Frequent");
       //Collections.sort( words, Word.CompFreqAsc); 
       printWords(20);
       System.out.println();
       System.out.println( "All");
       //Collections.sort( words); 
       printWords( words.size());
   }

   /**  
   Read the input file and process it. 
   Input is on standard input and is read one 
   word at a time -- separated by whitespace. 
 
   All non alphabetic characters are stripped out and 
   words are all converted to lower case. 
  
   Any non-stopword word is stored in the list of words 
   and the number of occurrences is tracked.
   */
   private void readFile() 
   {
	   addStopwords();
	   while(inp.hasNext()) 
	   {
	       String word = inp.next().toLowerCase().trim().replaceAll( "[^a-z]","");
		   while(inp.hasNext()) {
		       if(word.length() < 0) {
				   Word w = new Word(word);
				   if(fill.isEmpty())
					   fill.add(w);
				   else if(findWord(w)) {
					   if(!isStopword(word)) {
						   fill.get(findIndex(w)).incrCount();
					   }
					   stopwordcount++;
				   }
				   else
					   fill.add(w);
				totalwordcount++;
		       }
	       }
	   }
   }
   
   public int genKey(String word) 
   {
	   int key = 0;
	   if(!word.isEmpty()) {
		   key += (int) word.charAt(0);
		   genKey(word.substring(1));
	   }
	   return key;
   }
   
   public boolean findWord(Word w) 
   {
	   boolean check = false;
	   for(int i = 0; i < fill.size(); i++) {
		   check = fill.get(i).equals(w);
	   }
	   return check;
   }
   
   public boolean isStopword(String word) {
	   boolean check = false;
	   for(int i = 0; i < stopwords.length; i++) {
		   if(word.equals(stopwords[i])) {
			   check = true;
		   }
	   }
	   return check;
   }
   
   public int findIndex(Word w) 
   {
	   int x = 0;
	   for(int i = 0; i < fill.size(); i++) {
		   if(fill.get(i).equals(w)) {
			   x = i;
		   }
	   }
	   return x;
   }
   
   public void addToHash() 
   {
	   int space = totalwordcount + stopwordcount;
	   words = new HashMap<Integer, Word>(space + ((int) (space * 0.25)));
	   for(int i = 0; i < fill.size(); i++) {
		   words.put(i, fill.get(i));
	   }
   }
   
   public void addStopwords() 
   {
	   for(int i = 0; i < stopwords.length; i++) {
		   Word w = new Word(stopwords[i]);
		   fill.add(w);
	   }
   }
   
   public void deleteStopWords() 
   {
	   while(words.containsValue(stopwords[1])) {}
   }

   /** Run the program. Read the file, then print the results. 
   */
   public void run() 
   {
      readFile();
      addToHash();
      deleteStopWords();
      printResults();
   }

}