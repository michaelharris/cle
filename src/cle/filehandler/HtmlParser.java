package cle.filehandler;

import java.util.List;
import java.util.Random;

import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

public class HtmlParser {

	public static String addElementIds(String html) {
	
		Source source = new Source(html);

		OutputDocument outputDocument = new OutputDocument(source);

		List<Element> elementList = source.getAllElements();
		for (Element element : elementList) {
			StartTag startTag = element.getStartTag();
			Attributes attributes = startTag.getAttributes();
			
			Random r = new Random();
			if (attributes == null || attributes.get("data-elementid") == null) {
				
				if(startTag.equals("param")) break;//don't want to adjust the object (video) param tag
				String newStartTag = element.getStartTag().tidy();
				newStartTag = newStartTag.substring(0,
						(newStartTag.length() - 1));
				
				StringBuilder builder = new StringBuilder();
				
				int elemNo = r.nextInt(999900) + 50;
				
				
				builder.append(newStartTag).append(" ")
						.append("data-elementid=\"").append(elemNo)
						.append("\"").append(">");
				//System.out.println("new tag " + builder);

				outputDocument.replace(startTag, builder);// update the tag
			} else {
				// The elementid already exists - no need to do anything

			}

		}
		
		return outputDocument.toString();
	}

}
