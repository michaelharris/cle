package cle.filehandler;

public class YouTube {

	
	public static String generateEmbed(String id){
		StringBuilder sf = new StringBuilder();
	
	sf.append(	  "<object type=\"application/x-shockwave-flash\" id=\"ytplayer\" ");
	sf.append("data=\"http://www.youtube.com/v/");
	sf.append(id);
	sf.append("?border=0&amp;enablejsapi=1&amp;");
	sf.append("playerapiid=ytplayer\"><param name=\"allowScriptAccess\" ");
	
	sf.append("value=\"always\" /><param name=\"bgcolor\" value=\"#cccccc\" /></object>");
		      
	
	
		return sf.toString();
		
		
	}
}
