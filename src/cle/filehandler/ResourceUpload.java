package cle.filehandler;

import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import cle.dao.ImageDao;
import cle.dao.PageDao;
import cle.domain.Image;
import cle.domain.Page;
import cle.domain.Resource;
import cle.filehandler.pdf.PdfToHtml;

@Service
public class ResourceUpload {
	@Autowired(required = true)
	PageDao pageDao;
	@Autowired(required = true)
	ImageDao imageDao;

	public boolean process(Resource resource) {
		boolean success = false;
		System.out.println("-----Processing resource----");
		String storeLoc = "/home/michael/foo/";
		String tempDirStr = null;
		File tempDir = null;
		try {
			tempDir = FileOps.createTempDir();
			tempDirStr = tempDir.getAbsolutePath() + "/";
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String tempLoc = "x.html";

		CommonsMultipartFile fileData = resource.getFileData();
		int resourceid = resource.getResourceid();
		String fileLoc = FileOps.fileToDisk(fileData, resourceid, storeLoc);
		PdfToHtml.convert(fileLoc, tempDirStr + tempLoc);
		
		// Handle the storage of images

		String filenames[] = tempDir.list();
		Set<Image> imageSet = new HashSet<Image>();

		for (int i = 0; i < filenames.length; i++) {
			String fileContent = null;
			String fileName = filenames[i];

			if (!fileName.contains("htm")) {

				try {
					BufferedImage file = ImageIO.read(new File(tempDirStr
							+ fileName));

					ByteArrayOutputStream bas = new ByteArrayOutputStream();

					ImageIO.write(file, "png", bas);

					byte[] data = bas.toByteArray();

					Image image = new Image();
					image.setImagename(fileName);
					image.setImagedata(data);
					image.setResource(resource);

					imageSet.add(image);

					imageDao.saveOrUpdate(image);
				}

				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (fileName.contains("htm")) {
				success = true;//If there are html pages we will assume success.
				Pattern intsOnly = Pattern.compile("\\d+");// Check that there
															// are integers in
															// the filename
				Matcher makeMatch = intsOnly.matcher(fileName);
				if (makeMatch.find()) {
					String inputInt = makeMatch.group(); // Match the pattern to
															// find the int
					int intFromFileName = Integer.parseInt(inputInt); //

					try {
						fileContent = FileOps.readFileAsString(tempDirStr + fileName);// get
																				// the
																				// textual
																				// body
																				// of
																				// the
																				// page
						if (fileContent.length() > 1) {
							// If there is file content save a page with the
							// correct page number;
							fileContent = HtmlParser.addElementIds(fileContent);
							// fileContent = addCSS(fileContent);
							Page page = new Page(resource, intFromFileName);
							page.setContent(fileContent);
							pageDao.saveOrUpdate(page);

						}

					} catch (IOException e) {

						e.printStackTrace();
					}

				}
			}

		}

		FileOps.deleteDirectory(tempDir);
		return success;
	}

	
}
