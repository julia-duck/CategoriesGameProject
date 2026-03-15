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
    public static void main(String[] args) {
        initCategories();
    }

    /** Sets up categories by reading from file */
    public static void initCategories() {
        allCategories = new HashMap<>();
        List<String> petWords = readFiles("pets.txt");
    }

    /** Reads file into an ArrayList
     * @param name of text file
     * @return ArrayList of words from file
     */
    public static List<String> readFiles(String filename) {
        List<String> list = new ArrayList<>();
        try {
            Scanner petp = new Scanner(new File(filename)); //pet parser
            while (petp.hasNextLine()) {
                list.add(petp.nextLine());
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File was not found.");
        }
        finally {
            return list;
        }
    }
}