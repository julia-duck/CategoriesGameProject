/** Word categories game, Takes a list of words from
 * one category and adds in an imposter word. Asks
 * the user which word doesn't fit in.
 * @author Julia Jeng
 * @author Grace Ding
 * Collaborators: Zoey Ding, Kevin Ko
 */
import java.io.*;
import java.util.*;
public class CategoriesGame {
    private static Map<String, List<String>> allCategories;
    private static List<String> categoryNames, cat1, cat2;
    private static int streak;
    private static int bestStreak;
    public static void main(String[] args) {
        initCategories();
        boolean playingGame = true;
        while (playingGame)
        {
            chooseCategories();
            //get word lists from chosen categories
            playingGame = playGame(5, 2, cat1, cat2); 
            //duckRecursion();
            System.out.println("Current streak: " + streak);
            System.out.println("Best streak: " + bestStreak);
            System.out.println();
        }
        System.out.println("Thanks for playing!");
        System.out.println("Your best streak was " + bestStreak + " correct in a row!");
    }

    /** Sets up/initializes categories by reading from files */
    public static void initCategories() {
        allCategories = new HashMap<>();
        //new File object of current working directory
        File categories = new File(".");
        //Creates list of files in the directory that end with .txt
        File[] txtFiles = categories.listFiles(file -> file.getName().endsWith(".txt"));

        //add each category to the hashmap
        for (File fl : txtFiles)
        {
            String name = fl.getName();
            List<String> words = readFiles(name);
            if (words != null) {
                categoryNames.add(name);
                allCategories.put(name, words);
            }
        }
        streak = 0;
        bestStreak = 0;
    }

    /** Reads file into an ArrayList
     * @param name of text file (.txt)
     * @return ArrayList of words from file
     */
    public static List<String> readFiles(String filename) {
        List<String> list = new ArrayList<>();
        //try with resources, closes automatically after try block
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                list.add(fileScanner.nextLine());
            }
            if (list.isEmpty()) {
                throw new Exception("Empty list found!");
            }
            return list;
        }
        catch (FileNotFoundException e) {
            System.out.println("File " + filename + " was not found.");
            return null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /** Randomly chooses two categories in the names list to pull words from
     *  and modifies Lists cat1 and cat2 to contain the words in these categories
     */
    public static void chooseCategories()
    {
        String[] chosenCategories = new String[2];
        List<String> dontInclude = Arrays.asList(new String[]{"composers", "pasta"});

        //clone category names list so that names can safely be removed
        List<String> categoriesToChoose = new ArrayList<>();
        for (String name : categoryNames)
        {
            if (!dontInclude.contains(name)) {
                categoriesToChoose.add(name);
            }
        }
        
        //choose first category
        int randomIdx1 = (int) (Math.random() * categoriesToChoose.size());
        chosenCategories[0] = categoriesToChoose.remove(randomIdx1);

        //choose second category
        int randomIdx2 = (int) (Math.random() * categoriesToChoose.size());
        chosenCategories[1] = categoriesToChoose.remove(randomIdx2);

        cat1 = allCategories.get(chosenCategories[0]);
        cat2 = allCategories.get(chosenCategories[1]);
    }

    /** Overloaded chooseCategories method
     * Instantiates cat1 and cat2 to specified categories
     * @param category1, name of the first category (without .txt)
     * @param category2, name of the second category
     * @return true or false if the categories were initialized
     */
    public static boolean chooseCategories(String category1, String category2) {
        if (allCategories.containsKey(category1) && allCategories.containsKey(category2)) {
            cat1 = allCategories.get(category1);
            cat2 = allCategories.get(category2);
            return true;
        }
        else {
            return false;
        }
    }

    /**Picks two categories and prints game to console
     * @param number of words to give user
     * @param chances, the number of tries the user has
     * @param firstCat first category words: all but one word comes from here
     * @param secCat second category words: one imposter word comes from here
     * @return whether or not to continue playing
     * Precondition: firstCat and secCat are not null and have at least one word
     */
    public static boolean playGame(int numWords, int chances, List<String> firstCat, List<String> secCat) {
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
                System.out.print("Which word doesn't fit in (Type the number or -1 to quit): ");
                String in = input.next();
                try {
                    ans = Integer.parseInt(in);
                    if (ans > 0 && ans <= numWords) {
                        valid = true;
                    }
                    else if (ans < 0)
                    {
                        return false;
                    }
                    else {
                        System.out.println("Please type a number from 1 to " + numWords);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please type a valid number.");
                }
            }
            if (ans-1 == imposterIdx) { //account for 0 index
                streak ++;
                if (streak > bestStreak) bestStreak = streak;
                System.out.println("You guessed correctly!");
                correct = true;
            }
            else {
                tries++;
                streak = 0;
                System.out.println("Incorrect guess. You have " + (chances - tries) + " tries left.");
                if (chances-tries == 0) {
                    System.out.println("The correct answer is " + (imposterIdx + 1) + ", " +  secCat.get(0));
                }
            }
        }
        return true;
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