
/** 
 * Started by M. Moussavi
 * March 2015
 * Completed by: Nathan Jack
 * 
 * Lab 06 Code Exercise 3
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Nov 4, 2020
 * 
 * Reads objects from binary file. Requires filename with correct file path.
 */
 

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ReadRecord {

	private ObjectInputStream input;

	/**
     *  opens an ObjectInputStream using a FileInputStream
     */
    
    private void readObjectsFromFile(String name)
    {
        MusicRecord record = new MusicRecord();
        
        try
        {
            input = new ObjectInputStream(new FileInputStream( name ) );
        }
        catch ( IOException ioException )
        {
            System.err.println( "Error opening file." );
        }
        
        /* The following loop is supposed to use readObject method of
         * ObjectInputSteam to read a MusicRecord object from a binary file that
         * contains several records.
         * Loop should terminate when an EOFException is thrown.
         */
        try {
			while (true) // loop until end of text file is reached
			{
				record = (MusicRecord) input.readObject();
				
				System.out.print(record.getYear() + "  ");  // echo data read from text file
				System.out.print(record.getSongName() + "  ");       // echo data read from text file
				System.out.print(record.getSingerName() + "  ");  // echo data read from text file
				System.out.print(record.getPurchasePrice() + "  ");  // echo data read from text file
				System.out.print("\n");
				//record.
			}
		}
		catch (EOFException e){
			System.out.println("End of file.");
		} catch (ClassNotFoundException e) {
			System.err.println("Object type in file could not be matched.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("File not found.");
			e.printStackTrace();
		}
        
        try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public static void main(String[] args) {
		ReadRecord d = new ReadRecord();
		d.readObjectsFromFile("mySongs.ser");
		d.readObjectsFromFile("allSongs.ser");
	}
}