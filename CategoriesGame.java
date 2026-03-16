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
        /** ERROR skips like 2 indexes?? */
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
        while (!correct && tries < chances) {
            /** ERROR infinite loop */
            boolean valid = false;
            int ans = 0;
            while (!valid) { 
                System.out.print("Which word doesn't fit in (Type the number): ");
                if (input.hasNextInt()) {
                    ans = input.nextInt();
                    valid = true;
                }
                else {
                    input.next();
                }
            }
            //int ans = input.nextInt();
            if (ans == imposterIdx) {
                System.out.println("You guessed correctly!");
                correct = true;
            }
            else {
                tries++;
                System.out.println("Incorrect guess. You have " + (chances - tries) + " tries left.");
            }
        }
    }
}