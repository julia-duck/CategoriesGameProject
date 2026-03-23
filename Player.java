/**
 * Player class stores/handles a game player's stats
 */
public class Player
{
    private String name;
    private int score;
    private int currentStreak;
    private int bestStreak;

    public Player(String playerName)
    {
        name = playerName;
        score = 0;
        currentStreak = 0;
        bestStreak = 0;
    }

    /** Updates the player's score and streak based on if they got a question right
     * @param correct whether or not the player got a question correct
     * @return the player's updated score
     */
    public int updateScore(boolean correct)
    {
        if (correct)
        {
            score ++;
            currentStreak ++;
            if (currentStreak > bestStreak)
            {
                bestStreak = currentStreak;
            }
        }
        else
        {
            currentStreak = 0;
        }
        return score;
    }

    /** Gets the player's name
     * @return the player name
     */
    public String getName()
    {
        return name;
    }

    /** Gets the player's total score (number of correct answers)
     * @return the player's total score
     */
    public int getTotalScore()
    {
        return score;
    }

    /** Gets the player's current streak
     * @return the player's current streak
     */
    public int getCurrentStreak()
    {
        return currentStreak;
    }

    /** Gets the player's best streak
     * @return the player's best streak
     */
    public int getBestStreak()
    {
        return bestStreak;
    }
}