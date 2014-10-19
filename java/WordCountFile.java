

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
 
public class WordCountFile {

    /**
     * use a MappedByteBuffer to wrap a huge file. Using a MappedByteBuffer does
     * not load the file in JVM but reads it directly off the file system
     * memory. The file can be opened in read, write or private mode.
     */
    // to test you can use any video movie file if you dont have any other large
    // file for testing.
	
    private static String hugeFile = "/home/sandeep/Downloads/10gbwiki";
 
    public static void main(String[] args) throws IOException, ClassNotFoundException {
    	Map<String,Integer> mapWordCount = new HashMap<String,Integer>();
    	Scanner sc2 = null;
    	int oldCount;
        try {
            sc2 = new Scanner(new File(hugeFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  
        }
        int words= 0;
       while (words<1000) {
                Scanner s2 = new Scanner(sc2.nextLine());
            boolean b;
            while (b = s2.hasNext()) {
                String s = s2.next();
                if(mapWordCount.containsKey(s)){
                	oldCount=mapWordCount.get(s);
                	mapWordCount.put(s, oldCount+1);
                }
                else
                	mapWordCount.put(s, 1);
                System.out.println(words++);
                if(words>1000)
                	break;
            }
            File file = new File("temp");
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.writeObject(mapWordCount);
            s.close();
 
    }
        File file = new File("temp");
        FileInputStream f = new FileInputStream(file);
        ObjectInputStream s = new ObjectInputStream(f);
        HashMap<String, Object> fileObj2 = (HashMap<String, Object>) s.readObject();
        s.close();
        int Total=0;
        System.out.println(fileObj2.size());
        for(Map.Entry<String,Object> entry : fileObj2.entrySet()){
        	  //if(entry.getValue().equals("delete")) // replace with your own check
        	Total=Total+(int)entry.getValue();
        	System.out.println(Total);
        	}
    }
}


