// Stephen Reader
// 10552526

// Import
import java.io.*;
import java.util.*;

public class Main
{
    // Main is being used as the blackboard
    public static void main (String args[])
    {
        // ************** Getting file name and storing values **************

        // Initialize ArrayList used to store numbers from file
        ArrayList numArray = new ArrayList();

        // Get file name from user
        Scanner fileInput = new Scanner(System.in);
        System.out.print("Please enter your .txt file (Example: b1.txt): ");
        String theFile = fileInput.next(); // Scans the next token of the input as an int.
        fileInput.close();

        // Try and get the array list using readFile function
        try
        {
            numArray = readFile(theFile);
            //System.out.println(numArray);
        }
        catch(IOException exc)
        {
            System.out.println("I/O Error: " + exc);
        }


        // ************** The blackboard **************

        // Create random number generator
        Random rand = new Random();

        // Variables used to keep track of things happening
        Boolean done = false;
        Boolean wait = true;
        int i = 0;
        int frame = 0;
        int x;
        int score = 0;

        // Loop until game is over according to done_bool()
        do
        {
            if(frame < 10) {
                // Get random number and send blackboard to one of the functions
                x = (rand.nextInt(40000) + 1) % 3;

                // If 1, 2, or 3 do function
                if (x == 0) {
                    // Set the boolean value to done or not done
                    done = done_bool(frame, done);
                } else if (x == 1) {
                    // Make sure that currentScore() runs before roll()
                    if (wait == true) {
                        //System.out.println("Frame: " +frame);
                        frame++;

                        score = currentScore(numArray, done, i, score, frame, wait);
                        // Current score has been calcualated so frame must move up by 1
                        System.out.println("Current Score in frame " + frame + " is : " + score);


                        // Set wait to false so that i can run
                        wait = false;
                    }
                } else {
                    // Make sure that currentScore() has ran
                    if (wait == false) {
                        // Set new i which tells where we are at in the ArrayList, sort of like a pointer
                        i = roll(numArray, done, i, frame);
                        // Set wait to false so that currentScore can run
                        wait = true;
                    }
                }
            }
            else if(frame == 10)
            {
                done = true;
            }
        } while (done == false);


        // Done so print final score
        int final_score = currentScore(numArray,done,i, score,frame,wait);
        System.out.println("\nThe final score is: " + final_score);
    }// End main


    // Sets location of where we are in the array list. i represents rolls (or a pointer)
    public static int roll(ArrayList numArray, Boolean done, int i, int frame)
    {

        // Make sure game is not done
        if (done == false)
        {
            // If strike
            if ((Integer)numArray.get(i) == 10)
            {
                i++;
                return i;
            }
            // If spare or open frame
            else
            {
                i = i + 2;
                return i;
            }
        }
        // If this return is reached, then game must be done and we are waiting for blackboard to call a different function
        return i;

    }// End roll


    // Decides if game is done or not
    public static boolean done_bool(int frame, Boolean done)
    {
        // If it is the 10th frame then game is over
        if(frame == 10)
        {
            done = true;
            return done;
        }
        // If this return is reached then the game is not over
        return done;
    }// End done_bool


    public static int currentScore(ArrayList numArray, Boolean done, int i, int score, int frame, Boolean wait)
    {
        // Make sure that the game is not already over
        if(done == false)
        {
            // If the frame is 1-9
            if (frame < 10)
            {
                    // If throw is a strike
                    if ((Integer) numArray.get(i) == 10)
                    {
                        score = score + ((Integer) numArray.get(i)) + ((Integer) numArray.get(i + 1)) + ((Integer) numArray.get(i + 2));
                    }

                    // If throws are spare
                    else if ((Integer) numArray.get(i) != 0 && (Integer) numArray.get(i + 1) != 0 && (Integer) numArray.get(i) + (Integer) numArray.get(i + 1) == 10 && frame < 10)
                    {
                        score = score + ((Integer) numArray.get(i)) + ((Integer) numArray.get(i + 1)) + ((Integer) numArray.get(i + 2));
                    }

                    // If throws are OpenFrame
                    else
                    {
                        score = score + ((Integer) numArray.get(i)) + ((Integer) numArray.get(i + 1));
                    }
            }

            // If it is the 10th frame
            else if (frame == 10)
            {
                // 10th frame
                // Not every file has enough ints.  Catch is used at the end to assume there is a 0 at the end.
                try
                {
                    score = score + (Integer) numArray.get(i) + (Integer) numArray.get(i + 1) + (Integer) numArray.get(i + 2);

                }
                catch (IndexOutOfBoundsException e)
                {
                    score = score + (Integer) numArray.get(i) + (Integer) numArray.get(i + 1);
                }
            }
        }
        return score;
    }// End currentScore


    // ************** Used for reading the file **************

    // Function to get the parts of the file into the array list
    public static ArrayList<Integer> readFile (String theFile)throws IOException
    {
        // Create file
        File setf = new File(theFile);

        // Use scanner to get into file
        Scanner scanner = new Scanner(setf);

        // ArrayList to hold the numbers in the file
        ArrayList numArray = new ArrayList();

        // Loop to insert into the ArrayList
        while (scanner.hasNextInt())
        {
            // Add int to ArrayList
            numArray.add(scanner.nextInt());
        }
        // Done with scanner
        scanner.close();

        // Return array
        return numArray;
    }// End readFile function
} // End class Main
