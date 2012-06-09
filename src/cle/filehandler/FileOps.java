package cle.filehandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.UUID;
import javax.activation.MimetypesFileTypeMap;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileOps {
	/**
	 * Create a new temporary directory. Use something like
	 * {@link #recursiveDelete(File)} to clean this directory up since it isn't
	 * deleted automatically
	 * @return  the new directory
	 * @throws IOException if there is an error creating the temporary directory
	 */
	public static File createTempDir() throws IOException
	{
	    final File sysTempDir = new File(System.getProperty("java.io.tmpdir"));
	    File newTempDir;
	    final int maxAttempts = 9;
	    int attemptCount = 0;
	    do
	    {
	        attemptCount++;
	        if(attemptCount > maxAttempts)
	        {
	            throw new IOException(
	                    "The highly improbable has occurred! Failed to " +
	                    "create a unique temporary directory after " +
	                    maxAttempts + " attempts.");
	        }
	        String dirName = UUID.randomUUID().toString();
	        newTempDir = new File(sysTempDir, dirName);
	    } while(newTempDir.exists());

	    if(newTempDir.mkdirs())
	    {
	        return newTempDir;
	    }
	    else
	    {
	        throw new IOException(
	                "Failed to create temp dir named " +
	                newTempDir.getAbsolutePath());
	    }
	}

	/**
	 * Recursively delete file or directory
	 * @param fileOrDir
	 *          the file or dir to delete
	 * @return
	 *          true iff all files are successfully deleted
	 */
	public static boolean recursiveDelete(File fileOrDir)
	{
		System.out.println("path" + fileOrDir.getAbsolutePath());
	    if(fileOrDir.isDirectory())
	    {
	    	System.out.println("is a directory --");
	    	
	        // recursively delete contents
	        for(File innerFile: fileOrDir.listFiles())
	        {
	        	System.out.println("list " + innerFile.getAbsolutePath());
	            if(recursiveDelete(innerFile))
	            {
	                return false;
	            }
	        }
	    }

	    return fileOrDir.delete();
	}
	
	 static public boolean deleteDirectory(File path) {
		    if( path.exists() ) {
		      File[] files = path.listFiles();
		      for(int i=0; i<files.length; i++) {
		         if(files[i].isDirectory()) {
		           deleteDirectory(files[i]);
		         }
		         else {
		           files[i].delete();
		         }
		      }
		    }
		    return( path.delete() );
		  }
	
	public static boolean recursiveDeleteDir(File Dir)
	{
	    if(Dir.isDirectory())
	    {
	    	System.out.println("is dir");
	        // recursively delete contents
	        for(File innerFile: Dir.listFiles())
	        {
	            if(recursiveDelete(innerFile))
	            {
	                return false;
	            }
	        }
	    }

	    return true;
	}
	
	
	public static String fileToDisk(CommonsMultipartFile commonsMultipartFile, Integer id, String storeLoc){
		//this moves a CommondMultipartFile to somewhere on disk
		
		String origName = removeSpaces(commonsMultipartFile.getOriginalFilename());
		String newLoc = storeLoc +id + "_"+  origName;
		
		try {
			
			commonsMultipartFile.transferTo(new File(newLoc));
			return newLoc;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	
	 public static String getMimeType(String fileUrl)
	 {
		 File f = new File(fileUrl);
		 
		 String contentType = new MimetypesFileTypeMap().getContentType(f);
		 return contentType;
		 
	  }
	 
	 public static String readFileAsString(String filePath)
		throws java.io.IOException {
	byte[] buffer = new byte[(int) new File(filePath).length()];
	BufferedInputStream f = null;
	try {
		f = new BufferedInputStream(new FileInputStream(filePath));
		f.read(buffer);
	} finally {
		if (f != null)
			try {
				f.close();
			} catch (IOException ignored) {
			}
	}
	return toUTF8(buffer);
}
	 
	 public static String toUTF8(byte[] bArray){
		 
		 try {
			byte[] utf8 = new String(bArray, "ISO-8859-1").getBytes("UTF-8");
			return new String(utf8);
		} catch (UnsupportedEncodingException e) {
			// Ignore exception and return original string
			return new String (bArray);
		}
		 
		 
	 }
	 
	 public static String removeSpaces(String string) {
		  StringTokenizer st = new StringTokenizer(string," ",false);
		  String s="";
		  while (st.hasMoreElements()) s += st.nextElement();
		  return s;
		}
}
