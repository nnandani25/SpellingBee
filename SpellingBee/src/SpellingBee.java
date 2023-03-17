import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Spelling Bee
 *
 * This program accepts an input of letters. It prints to an output file
 * all English words that can be generated from those letters.
 *
 * For example: if the user inputs the letters "doggo" the program will generate:
 * do
 * dog
 * doggo
 * go
 * god
 * gog
 * gogo
 * goo
 * good
 *
 * It utilizes recursion to generate the strings, mergesort to sort them, and
 * binary search to find them in a dictionary.
 *
 * @author Zach Blick, [ADD YOUR NAME HERE]
 *
 * Written on March 5, 2023 for CS2 @ Menlo School
 *
 * DO NOT MODIFY MAIN OR ANY OF THE METHOD HEADERS.
 */
public class SpellingBee {

    private String letters;
    private ArrayList<String> words;
    public static final int DICTIONARY_SIZE = 143091;
    public static final String[] DICTIONARY = new String[DICTIONARY_SIZE];

    public SpellingBee(String letters) {
        this.letters = letters;
        words = new ArrayList<String>();
    }

    // TODO: generate all possible substrings and permutations of the letters.
    //  Store them all in the ArrayList words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    // Generates all of the possible words.
    public void generate()
    {
        // Calls make words and takes in the part which starts empty and the letters.
        makeWords("", letters);
    }

    // Finds all the possible words in the given letters.
    public void makeWords(String part, String remainingLet)
    {
        // Adds the part to the word list.
        words.add(part);

        // Base Case: if there are no more remaining letters, then it has found all the possible solutions in that
        // Branch and can return.
        if(remainingLet.length() == 0)
        {
            return;
        }

        // If there are more than 0 remaining letters, it loops through the remaining letters.
        for(int i = 0;  i < remainingLet.length(); i++)
        {
            // Takes the ith part of the string, which would be the starting letter of that branch.
            String startLet = remainingLet.substring(i,i+1);
            // Takes all the letters before the starting letter.
            String beforeLet = remainingLet.substring(0, i);
            // Takes the letters after the starting letter.
            String afterLet = remainingLet.substring(i+1);
            // Runs make words with the startLet and the part, and the remaining letters as all the other letters
            // Besides the part.
            makeWords(startLet + part, beforeLet + afterLet);
        }

    }

    // TODO: Apply mergesort to sort all words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    // Sorts the word list alphabetically
    public void sort()
    {
        // Calls merge sort with the low as the beginning of the array and high as the end of the array.
        mergeSort(0, words.size() - 1);
    }

    // Does mergeSort on the array.
    public void mergeSort(int low, int high)
    {
        // Returns if the low index is greater than or equal to the high.
        if(low >= high)
        {
            return;
        }

        // Mid is the index of the middle of the array.
        int mid = (low+high)/2;
        // Makes a recursive call with the first half of the array.
        mergeSort(low, mid);
        // Calls itself with the second half of the array.
        mergeSort(mid + 1,high);
        // Calls merge in order to merge the two arrays.
        // Takes in the indexes for the low, high, and mid.
        merge(low, high, mid);
    }

    // Merges the two arrays.
    public void merge(int low, int high, int mid)
    {
        // Creates a new array in which the two arrays will merge into.
        ArrayList<String> copy = new ArrayList<String>();
        int i = low;
        int j = mid+1;

        // Compares the words alphabetically until one of the arrays runs out of letters to compare to.
        while(i <= mid && j <= high)
        {
            // If the word in i position (on the first half of the word list) is higher up alphabetically than the word
            // in j position (on the second half of the word list), than the word at i position gets added to the words list.
            if(words.get(i).compareTo(words.get(j)) < 0)
            {
                copy.add(words.get(i));
                // i is increased so there.
                i++;
            }
            // if the word at j is alphabetically higher than the word at i, than the word at j gets added.
            else
            {
                copy.add(words.get(j));
                j++;
            }
        }
        // If upper half of the word list is smaller than the lower half, than it automatically brings down the leftover
        // Words from the first half of the word list.
        while(i <= mid)
        {
            copy.add(words.get(i));
            i++;
        }
        // If lower half of the word list is smaller than the upper half, than it automatically brings down the leftover
        // Words from the second half of the word list.
        while(j <= high)
        {
            copy.add(words.get(j));
            j++;
        }

        // Moves everything from copy back into word list.
        for(int k = 0; k < copy.size(); k++)
        {
            words.set(low+k, copy.get(k));

        }
    }


    // TODO: For each word in words, use binary search to see if it is in the dictionary.
    //  If it is not in the dictionary, remove it from words.
    // Checks to see if the words in words are in the dictionary.
    public void checkWords()
    {
        // Goes through the word list and checks if the word was not found.
        for(int i = 0; i < words.size(); i++)
        {
            // If the word was not found in the dictionary, it removes it from the word list.
            if(!(found(words.get(i), 0, DICTIONARY.length-1)))
            {
                words.remove(i);
                // Decrements i, so it doesn't skip an index.
                i--;
            }
        }
    }

    // The string returns true if the string was found in the dictionary.
    public boolean found(String target, int low, int high)
    {
        // Returns false if the word is not in the dictionary, which would mean that low is greater than high.
        if(low > high)
        {
            return false;
        }

        // Finds the middle index.
        int med = (high+low)/2;

        // Checks if the word in the middle index of the dictionary is equal to the target word and retuns true if it is.
        if(DICTIONARY[med].equals(target))
        {
            return true;
        }

        // Checks if the target is alphabetically higher than the middle index and if so changes high.
        else if(DICTIONARY[med].compareTo(target) > 0)
        {
            // High becomes the index before the middle index.
            high = med - 1;
        }

        // If it gets to this, then target is alphabetically lower than the middle index.
        else
        {
            // Low becomes the index after the middle.
            low = med + 1;
        }

        // Repeats until it returns false or finds the word.
        return found(target, low, high);

    }

    // Removes duplicates from the sorted list.
    public void removeDuplicates() {
        int i = 0;
        while (i < words.size() - 1) {
            String word = words.get(i);
            if (word.equals(words.get(i + 1)))
                words.remove(i + 1);
            else
                i++;
        }
    }

    // Prints all valid words to wordList.txt
    public void printWords() throws IOException {
        File wordFile = new File("Resources/wordList.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(wordFile, false));
        for (String word : words) {
            writer.append(word);
            writer.newLine();
        }
        writer.close();
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public SpellingBee getBee() {
        return this;
    }

    public static void loadDictionary() {
        Scanner s;
        File dictionaryFile = new File("Resources/dictionary.txt");
        try {
            s = new Scanner(dictionaryFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open dictionary file.");
            return;
        }
        int i = 0;
        while(s.hasNextLine()) {
            DICTIONARY[i++] = s.nextLine();
        }
    }

    public static void main(String[] args) {

        // Prompt for letters until given only letters.
        Scanner s = new Scanner(System.in);
        String letters;
        do {
            System.out.print("Enter your letters: ");
            letters = s.nextLine();
        }
        while (!letters.matches("[a-zA-Z]+"));

        // Load the dictionary
        SpellingBee.loadDictionary();

        // Generate and print all valid words from those letters.
        SpellingBee sb = new SpellingBee(letters);
        sb.generate();
        sb.sort();
        sb.removeDuplicates();
        sb.checkWords();
        try {
            sb.printWords();
        } catch (IOException e) {
            System.out.println("Could not write to output file.");
        }
        s.close();
    }
}
