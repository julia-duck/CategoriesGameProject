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
        System.out.println(Arrays.toString(chooseCategories())); //test
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
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextLine()) {
                list.add(fileScanner.nextLine());
            }
            fileScanner.close();
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
}