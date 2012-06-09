package cle.filehandler.pdf;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PdfToHtml {

	public static void convert(String filename, String dest) {
		try {
			Runtime rt = Runtime.getRuntime();
			// Process pr = rt.exec("cmd /c dir");
			String cmd = "/home/michael/pdftohtml -c -zoom 2 " + filename +" " +  dest;
		
			//System.out.println("Command " +cmd);
			Process pr = rt
					.exec(cmd);

			BufferedReader input = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));
//Get standard error output
			BufferedReader errors = new BufferedReader(new InputStreamReader(
					pr.getErrorStream()));

			String line = null;
			System.out.println("cmd output:");
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}

			int exitVal = pr.waitFor();

			System.out.println("PdfToHtml: Exited with error code (0 = success): " + exitVal);
//print errors
			System.out.println("cmd was: " + cmd);
			while ((line = errors.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
}