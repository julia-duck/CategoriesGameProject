/** Word categories game, Takes a list of words from
 * one category and adds in an imposter word. Asks
 * the user which word doesn't fit in.
 * @author Julia Jeng
 * @author Grace Ding
 * Collaborators: Zoey Ding
 */
import java.io.*;
import java.util.*;
public class CategoriesGame {
    private static Map<String, List<String>> allCategories;
    private static List<String> categoryNames;
    public static void main(String[] args) {
        initCategories();
        String[] categories = chooseCategories();
        //get word lists from chosen categories
        List<String> cat1 = allCategories.get(categories[0]);
        List<String> cat2 = allCategories.get(categories[1]);
        playGame(5, 2, cat1, cat2); 
        //duckRecursion();
    }

    /** Sets up categories by reading from files */
    public static void initCategories() {
        allCategories = new HashMap<>();
        categoryNames = Arrays.asList(new String[]{"pets", "cooking", "sports"});

        //add each category to the hashmap
        for (String name : categoryNames)
        {
            List<String> words = readFiles(name + ".txt");
            allCategories.put(name, words);
        }
    }

    /** Reads file into an ArrayList
     * @param name of text file
     * @return ArrayList of words from file
     */
    public static List<String> readFiles(String filename) {
        List<String> list = new ArrayList<>();
        //try with resources, closes automatically after try block
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                list.add(fileScanner.nextLine());
            }
            return list;
        }
        catch (FileNotFoundException e) {
            System.out.println("File " + filename + " was not found.");
            //note: right now other code may break if this happens
            return null;
        }
    }

    /** Randomly chooses two categories in the names list to pull words from
     *  @return a two element list containing the names of the two categories chosen
     */
    public static String[] chooseCategories()
    {
        String[] chosenCategories = new String[2];

        //clone category names list so that names can safely be removed
        List<String> categoriesToChoose = new ArrayList<>();
        for (String name : categoryNames)
        {
            categoriesToChoose.add(name);
        }
        
        //choose first category
        int randomIdx1 = (int) (Math.random() * categoriesToChoose.size());
        chosenCategories[0] = categoriesToChoose.remove(randomIdx1);

        //choose second category
        int randomIdx2 = (int) (Math.random() * categoriesToChoose.size());
        chosenCategories[1] = categoriesToChoose.remove(randomIdx2);

        return chosenCategories;
    }

    /**Picks two categories and prints game to console
     * @param number of words to give user
     * @param chances, the number of tries the user has
     * @param firstCat list of words in first category
     * @param secCat list of words in second category
     */
    public static void playGame(int numWords, int chances, List<String> firstCat, List<String> secCat) {
        //makes sure words are picked randomly
        Collections.shuffle(firstCat);
        Collections.shuffle(secCat);

        //scanner to read user's answer
        Scanner input = new Scanner(System.in);

        //actual number of words to print (prevent index out of bounds)
        int words = Math.min(firstCat.size(), numWords);

        //pick the index of the word that doesn't fit in
        int imposterIdx = (int)(Math.random()*words);

        //Pick words 
        for (int i = 0; i < words; i++) {
            if (i != imposterIdx) {
                System.out.println(i+1 + ": " + firstCat.get(i));
            }
            else {
                //get(0) to avoid index out of bounds if secCat.size() < firstCat.size()
                System.out.println(i+1 + ": " + secCat.get(0));
            }
        }
        boolean correct = false;
        int tries = 0;
        //This loop allows user to keep guessing
        while (!correct && tries < chances) {
            boolean valid = false;
            int ans = 0;
            while (!valid) { 
                System.out.print("Which word doesn't fit in (Type the number): ");
                String in = input.next();
                try {
                    ans = Integer.parseInt(in);
                    if (ans > 0 && ans <= numWords) {
                        valid = true;
                    }
                    else {
                        System.out.println("Please type a number from 1 to " + numWords);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please type a valid number.");
                }
            }
            if (ans-1 == imposterIdx) { //account for 0 index
                System.out.println("You guessed correctly!");
                correct = true;
            }
            else {
                tries++;
                System.out.println("Incorrect guess. You have " + (chances - tries) + " tries left.");
            }
        }
    }
    /** Shhh no cheating, try it yourself first */
    public static void duckRecursion() {
        Scanner input = new Scanner(System.in);
        String ans = "01101000 01101001 01100100 01100100 01100101 01101110 00100000 01100010 01100001 01110011 01100101 00100000 01100011 01100001 01110011 01100101";
        StringBuilder secret = new StringBuilder("");
        String[] message = ans.split(" ");
        for (String el: message) {
            secret.append((char)(Integer.parseInt(el, 2)));
        }
        System.out.println("Quack! Quack! Quack! The mountain is being overrun by ducks! Quick, figure out how to stop the recursion!");
        System.out.print("Type hidden base case to stop the recursion: ");
        if (input.nextLine().equals(secret.toString())) {
            System.out.println("Success! Duck recursion terminating...");
        }
        else {
            System.out.println("Incorrect guess! Duck recursion continuing...");
            duckRecursion();
        }
    }
}