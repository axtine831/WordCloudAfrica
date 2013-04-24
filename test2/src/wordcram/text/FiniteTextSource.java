package wordcram.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import wordcram.Word;

/**
 * 
 * This class takes a set of input words and generates text to layout from those words.
 * The usecase for this class is when the created cram shall be rather big, but the number of words is limited.
 * @author simpsus
 *
 */
public class FiniteTextSource {
	
	private List<Word> words = new ArrayList<Word>();
	
	public void addWeight(String word, Float weight, Integer appearances) {
//		System.out.println("Adding " + word + weight +"|"+ appearances);
		for (int i=0;i<appearances;i++) {
			words.add(new Word(word,weight));
		}
	}
	
	public Word[] getWords() {
		Word[] arr = new Word[words.size()];
        System.out.println("returning " + words.size());
		int i =0;
		for (Word w:words) {
			arr[i++] = w;
			System.out.println(w);
		}
		return arr;
	}
	
	public static FiniteTextSource fromStrings(String[] text, int number) {
		FiniteTextSource result = new FiniteTextSource();
		Float weight;
		int occurences = 1;
		int k=0;
		int total = 0;
		while (total + Math.pow(2, k) <= number) {
			weight = new Float(1/(Math.pow(2,k)));
			occurences = (int) (Math.pow(2, k) / text.length);
			for (int i=0;i<text.length;i++) {
				result.addWeight(text[i], weight, occurences);
				total += occurences;
			}
			k++;
		}
		return result;
	}
	
		
}
