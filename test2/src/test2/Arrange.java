package test2;
import java.util.*;
import wordcram.*;
public class Arrange implements Comparator<Word> {
	

        public int compare(Word o1, Word o2) {
                if (o1.word.length() > o2.word.length()) {
                        return 1;
                } else if (o1.word.length() < o2.word.length()) {
                        return -1;
                } else {
                        return 0;
                }
        }
}