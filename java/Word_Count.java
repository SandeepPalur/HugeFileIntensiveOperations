
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
 
public class Word_Count {
    /**
     * use a MappedByteBuffer to wrap a huge file. Using a MappedByteBuffer does
     * not load the file in JVM but reads it directly off the file system
     * memory. The file can be opened in read, write or private mode.
     */
    // to test you can use any video movie file if you dont have any other large
    // file for testing.
    private static String hugeFile = "/home/sandeep/Downloads/10gbwiki";
 
    public static void main(String[] args) throws IOException {
        File file = new File(hugeFile);
        FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
        System.out.println("Channel Size: "+fileChannel.size());
        MappedByteBuffer buffer1 = fileChannel.map(
                FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
        MappedByteBuffer buffer2 = fileChannel.map(
                FileChannel.MapMode.READ_ONLY, fileChannel.size()/5, fileChannel.size()/5);
        MappedByteBuffer buffer3 = fileChannel.map(
                FileChannel.MapMode.READ_ONLY, (fileChannel.size()/5)*2, fileChannel.size()/5);
        MappedByteBuffer buffer4 = fileChannel.map(
                FileChannel.MapMode.READ_ONLY, (fileChannel.size()/5)*3, fileChannel.size()/5);
        MappedByteBuffer buffer5 = fileChannel.map(
                FileChannel.MapMode.READ_ONLY, (fileChannel.size()/5)*4, fileChannel.size()/5);
       //System.out.println("Channel Size: "+);
        // the buffer now reads the file as if it were loaded in memory. note
        // that for smaller files it would be faster
        // to just load the file in memory
        // lets see if this buffer is loaded fully into memory
        System.out.println(buffer1.isLoaded());
        System.out.println(buffer2.isLoaded());
        System.out.println(buffer3.isLoaded());
        System.out.println(buffer4.isLoaded());
        System.out.println(buffer5.isLoaded());
        // the mappedbytebuffer can be used as a normal buffer to do read and/or
        // write operations
        // read the size
        System.out.println(buffer1.capacity());
        System.out.println(buffer2.capacity());
        System.out.println(buffer3.capacity());
        System.out.println(buffer4.capacity());
        System.out.println(buffer5.capacity());
     
 
    }
}