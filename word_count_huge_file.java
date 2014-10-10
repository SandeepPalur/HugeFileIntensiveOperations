import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class word_count_huge_file {
	final  int NO_THREADS=8;
	static String hugeFile = "/home/sandeep/Downloads/10gbwiki";
	 void fragmentInputFile(){
		ExecutorService exec = Executors.newFixedThreadPool(NO_THREADS);

		// use RandomAccessFile because it supports readFully()
		RandomAccessFile rFileObj;
		try {
			rFileObj = new RandomAccessFile(hugeFile, "r");
			rFileObj.seek(0L);
			System.out.println("File Size: "+rFileObj.length()+" bytes");

		while (rFileObj.getFilePointer() < rFileObj.length())
		{
		    int readSize = (int)Math.min(rFileObj.length()/(NO_THREADS*100), rFileObj.length() - rFileObj.getFilePointer());
		    final byte[] data =  new byte[readSize];
		    System.out.println("Thread "+Thread.currentThread().getId()+" reads from"+ rFileObj.getFilePointer()+ "bytes  to"+ (rFileObj.getFilePointer() + data.length));
		    rFileObj.readFully(data);
		    exec.execute(new Runnable() 
		    {
		        public void run() 
		        {
		        	String decoded;
					try {
						decoded = new String(data, "UTF-16");
						String[] words = decoded.split("\\s+");
						for(int i=0;i<words.length;i++)
							System.out.println(words[i]);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
		        	
		        }
		        
		    });
		    
		}
		
		exec.awaitTermination(10, TimeUnit.MINUTES);
		exec.shutdown();
		}
		  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		  catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	  
	public static void main(String args[]){
		word_count_huge_file obj =new word_count_huge_file();
		obj.fragmentInputFile();
		System.out.println("Task Completed!!");
		
	}

}
