package com.rstech.imagelib;

import java.awt.Graphics2D; 
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage; 
import java.awt.image.Raster; 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;
  

public class ImageProcessing {
	public static final String DEFAULT_IMAGE_PNG = "DefaultImage.png";
	private static final Class thisClass = ImageProcessing.class;
	private static final Logger logger = Logger.getLogger(thisClass);

	public static final String DB_TEST = "test";
	public static final String DB_INT = "int";
	public static final String DB_US = "us";
	public static final String DB_CA = "ca";
	public static final String DB_ARCHIVE = "archive";
	public static final String DB_ARCHIVE_UM = "um";
	public static  String ImageFolder = "C:/Users/r23ak/WatchImages";

	
	private static String OS = System.getProperty("os.name").toLowerCase();
	 
	 
 
	public static boolean isWindows() {
 
		return (OS.indexOf("win") >= 0);
 
	}
 
	public static boolean isMac() {
 
		return (OS.indexOf("mac") >= 0);
 
	}
 
	public static boolean isUnix() {
 
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
 
	public static boolean isSolaris() {
 
		return (OS.indexOf("sunos") >= 0);
 
	}
	 
	public static  com.rstech.imagelib.ImageResult getImage(String db, String docId,
			boolean cropping, boolean scalling, int height, int width,
			String intensityMethod) throws IOException {
		ImageResult result = new ImageResult();
		BufferedImage imageFile = getImageFile(db, docId); // get image file
															// name
		if (imageFile == null) {
			return null;
		}

		if (cropping == true) {
			int ht = imageFile.getHeight(); // get dimentions
			int wt = imageFile.getWidth();
			imageFile = cropImage(imageFile, ht, wt, intensityMethod);
		}

		// get the image
		if (scalling) {
			imageFile = scaleImage(imageFile, cropping, height, width);
		}
		// crop
		// scale and convert to jpeg.
		result.setImage(imageFile);
		return result;
	}

	private static BufferedImage scaleImage(BufferedImage image,
			boolean cropping, int height, int width) {
		String methodName = "scaleImage";

		logger.debug(methodName + "Max Height " + height + " , MaxWidth "
				+ width);

		int ht = image.getHeight(); // get dimentions
		int wt = image.getWidth();
		ht = image.getHeight();
		wt = image.getWidth();

		logger.debug(methodName + "Actual Height " + ht + " , Actual width "
				+ wt);

		double scale = getScale(ht, wt, height, width);
		int scaledHeight = (int) (ht * scale);
		int scaledWidth = (int) (wt * scale);

		BufferedImage scaledImage = new BufferedImage(scaledWidth,
				scaledHeight, BufferedImage.TYPE_INT_RGB);
		AffineTransform aft = AffineTransform.getScaleInstance(scale, scale);
		Graphics2D scaledG2 = scaledImage.createGraphics();
		scaledG2.drawImage(image, aft, null);

		logger.debug(methodName + "Scaling to Height " + scaledHeight
				+ " , Actual width " + scaledWidth);

		return scaledImage;
	}

	public static BufferedImage cropImage(BufferedImage image, int ht, int wt,
			String intensityMethod) {
		String methodName = "cropImage";
		Raster raster = image.getRaster();

		int threshold = getIntensityThreshold(intensityMethod, image);
		int startX = -1;
		int startY = -1;
		int endX = -1;
		int endY = -1;

		for (int y = 0; y < ht; y++) {
			for (int x = 0; x < wt; x++) {
				int val = getIntensity(x, y, raster
						.getPixel(x, y, (int[]) null));
				if (threshold > val) {
					if (startY == -1) {
						startY = y;
						// Log.logDebugMessage(logger, thisClass, methodName,
						// "Lock start Y to "+startY+" color = "+val +
						// " given thresh = "+threshold);
					} else {
						endY = y;
					}
					if (startX == -1) {
						startX = x;
						// Log.logDebugMessage(logger, thisClass, methodName,
						// "Lock start X to "+startX+" color = "+val+" given thresh = "+threshold);
					} else {
						if (x < startX) {
							startX = x;
							// Log.logDebugMessage(logger, thisClass,
							// methodName,
							// "mod start X to "+startX+" color = "+val+" given thresh = "+threshold);
						} else if (x > endX) {
							endX = x;
						}
					}
					// Log.logDebugMessage(logger, thisClass, methodName,
					// "Auto Crop white space to "+
					// startX+", "+startY+", "+endX+", "+endY);
				}
			}
		}

		if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
			int newX = Math.abs(endX - startX);
			int newY = Math.abs(endY - startY);
			logger.debug(methodName + "Max Height " + ht + " , MaxWidth " + wt);
			logger.debug(methodName + "Auto Crop white space to " + startX
					+ ", " + startY + ", " + newX + ", " + newY);
			image = image.getSubimage(startX, startY, newX, newY);
		}

		return image;
	}

	// function _getThresholdValue($im, $x, $y)
	// {
	// $rgb = ImageColorAt($im, $x, $y);
	// $r = ($rgb >> 16) & 0xFF;
	// $g = ($rgb >> 8) & 0xFF;
	// $b = $rgb & 0xFF;
	// $intensity = ($r + $g + $b) / 3;
	// return $intensity;
	// }
	//    

	/**
	 * TODO modify this function to make use of the IntensityThresholdMethodName
	 * value to determine which intensity algorythm. WHITE-N where n is a number
	 * - use a static intenstiy of white (255) less the given number. Default
	 * n=0 BLACK-N where n is a number - use a static intensity of black (0)
	 * plus the given number. Default n=0 HIST-N where n is a number - caculate
	 * a histogram of the image color average intensities per pixel find the
	 * value with at least N occurances closest to white (255). This algorythm
	 * is used to crop noisy jpgs.
	 */
	public static int getIntensityThreshold(String intensityMethodName,
			BufferedImage image) {
		return 0xF0;

	}

	private static int getIntensity(int x, int y, int[] data) {
		int intensity = 0;
		if (data.length == 1) {
			intensity = data[0];
		} else {
			int red = data[0];
			int green = data[1];
			int blue = data[2];
			intensity = (red + green + blue) / 3;
		}

		return intensity;
	}

	public static double getScale(int actualHeight, int actualWidth,
			int targetHeight, int targetWidth) {
		double widthScale = (double) targetWidth / actualWidth;
		double heightScale = (double) targetHeight / actualHeight;
		double scale = 1D;
		if (actualHeight > targetHeight || actualWidth > targetWidth) {
			if (actualWidth > targetWidth) {
				scale = widthScale;
			} else if (actualHeight > targetHeight) {
				scale = heightScale;
			}
		}
		return scale;
	}

	private static long total_count = 0;

	public static BufferedImage getImageFile(InputStream inputStream)
			throws IOException {
		String methodName = "getImageFile(InputStream)";
		BufferedImage bufferedImage = null;

		total_count++;
		logger.debug(methodName + "Total request = " + total_count);
		ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
		Iterator readers = (Iterator) ImageIO.getImageReaders(iis);
		for (; readers.hasNext() && bufferedImage == null;) {
			ImageReader reader = (ImageReader) readers.next();
			String imageName = reader.getClass().getName();

			// logger.info(methodName + "Reader: " +
			// reader.getClass().getName());
			// logger.info(methodName +
			// "Inside for loop of readers, file count = " +
			// GetImage.GetFileCount());

			iis.mark();
			reader.setInput(iis, false);
			BufferedImage image = null;
			try {
				image = reader.read(0);
			} catch (javax.imageio.IIOException e) {
				if (readers.hasNext()) {
					logger.error(methodName
							+ "The current reader failed to load the image : "
							+ imageName + " trying again.", e);
				}
				image = null;
			} finally {
				reader.dispose();
				/*
				 * if (total_count % 20 == 0) { String count =
				 * GetImage.GetFileCount(); int c = Integer.parseInt(count);
				 * System.gc(); logger.error(methodName +
				 * "Inside finally, file count before = " + count + " after = "
				 * + GetImage.GetFileCount()); }
				 */
			}
			if (image == null) {
				iis.reset();
				if (readers.hasNext() != true) {
					logger.error(methodName + "Failed to load image ["
							+ imageName + "]");
				}
				continue;
			} else {
				bufferedImage = image;
				break;
			}
		}

		return bufferedImage;
	}

	public static BufferedImage getImageFile(String db, String docId)
			throws IOException {
		String methodName = "getImageFile";
		String imageFileName = getImageFileName(db, docId);

		BufferedImage bufferedImage = null;
		if (imageFileName != null) {
			File imageFile = new File(imageFileName);
			if (imageFile.exists() && imageFile.canRead()) {
				FileInputStream imageStream = new FileInputStream(imageFileName);
				bufferedImage = getImageFile(imageStream);
			} else {
				logger.error(methodName
						+ "Image File Doesn't exist or is not readable ["
						+ imageFileName + "]");
			}
		} else {
			logger.error(methodName + "Unknown image for " + db + " id="
					+ docId);
		}
		return bufferedImage;
	}

	public static String getImageFileName(String db, String docId) {
		String methodName = "getImageFileName";
		String imageFileName = docId;
		String resultFileName = "";
		
		if (isUnix()) {
			ImageFolder = "/opt/image/";
		}
		
		if (imageFileName != null) {
			resultFileName = ImageFolder + imageFileName + ".jpg";
			File imageFile = new File(resultFileName);
			if (imageFile.exists() && imageFile.canRead()) {
				imageFile = null;
			 	return resultFileName;
			}
			resultFileName = ImageFolder +  imageFileName + ".png";
			imageFile = new File(resultFileName);
			if (imageFile.exists() && imageFile.canRead()) {
				imageFile = null;
			 	return resultFileName;
			} 
 			resultFileName = ImageFolder + imageFileName + ".gif";
 			imageFile = new File(resultFileName);
			if (imageFile.exists() && imageFile.canRead()) {
				imageFile = null;
			 	return resultFileName;
			} 
			logger.error(methodName
						+ "Image File Doesn't exist or is not readable ["
						+ imageFileName + "]");
			System.err.println(methodName
					+ "Image File Doesn't exist or is not readable ["
					+ imageFileName + "]");
		} else {
			logger.error(methodName + "Unknown image for " + db + " id="
					+ docId);
		} 
		
		String fileName = null;
		if (db != null && false == "".equals(db.trim())) {
			 

			 
		} else {
			logger.warn(methodName + "Illegal DB Name");
		}
		logger.info(methodName + "File Name [" + fileName + "]");

		return fileName;
	}

	public static String getTestFileName(String docId) {
		String methodName = "getTestFileName";
		String fileName = null;
		int id = -1;

		try {
			id = Integer.parseInt(docId);
		} catch (NumberFormatException e) {
			logger.error(methodName + "Bad documentId [" + docId + "]", e);
		}

		if (RSImageServiceConfig.IS_TEST_ENABLED && id >= 0
				&& RSImageServiceConfig.TEST_RECORDS != null
				&& id < RSImageServiceConfig.TEST_RECORDS.length) {
			fileName = RSImageServiceConfig.TEST_ROOT
					+ RSImageServiceConfig.TEST_RECORDS[id];
		}
		return fileName;
	}


	public static String getUsHashDir(String docId) {
		String methodName = "getUsHashDir()";
		StringBuilder sb = new StringBuilder();

		/*
		 * sb.append(docId.substring(6, 8)) .append(File.separator)
		 * .append(docId.substring(4, 6));
		 */
		logger.debug(methodName + "Hash Dest Dir [" + sb.toString() + "]");

		return sb.toString();
	}
   

	public static String getCaHashDir(String docId) {
		String methodName = "getCaHashDir()";
		StringBuilder sb = new StringBuilder();

		sb.append(docId.substring(5, 7)).append(File.separator).append(
				docId.substring(3, 5));

		logger.debug(methodName + "Hash Dest Dir [" + sb.toString() + "]");

		return sb.toString();
	}
 

	/**
	 * This method pulls the default result image from the jar (so that it is
	 * allways availible.
	 * 
	 * @return
	 */
	public static com.rstech.imagelib.ImageResult getDefaultImageForOutputType(String outputType) {
		String methodName = "getDefaultImage";
		String imageName = RSImageServiceConfig.DEFAULT_IMAGE_NAME_JPG;

		if (RSImageServiceConfig.OUTPUT_TYPE_PNG.equals(outputType)) {
			imageName = RSImageServiceConfig.DEFAULT_IMAGE_NAME_PNG;
		}
		logger.debug(methodName + "default image for [" + outputType + "] = "
				+ imageName);
		com.rstech.imagelib.ImageResult result = null;

		try {
			InputStream inputStream = null;
			File tmpFile = new File(imageName);

			if (tmpFile != null && tmpFile.exists()) {
				logger.debug(methodName + "loading file : " + imageName);
				inputStream = new FileInputStream(tmpFile);
			} else {
				logger.debug(methodName + "loading resource : " + imageName);
				java.net.URL url = thisClass.getResource(imageName);
				inputStream = thisClass.getResourceAsStream(imageName);
			}

			BufferedImage image = null;
			image = getImageFile(inputStream);

			if (image != null) {
				logger.debug(methodName + "Loaded successfully");
				result = new com.rstech.imagelib.ImageResult();
				result.setImage(image);
			}

		} catch (IOException e) {
			logger
					.debug(methodName + "Failed to load the default Image : "
							+ e);
		}

		return result;
	}

	 
	private static BufferedImage loadImage(String imgSrcFull) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new URL(imgSrcFull)); 
		} catch (IOException ex) {
			 
		}
		return bi;
	}

	public static com.rstech.imagelib.ImageResult fetchImage(String url, String id) throws IOException {
		com.rstech.imagelib.ImageResult result = null;
		try { 
			// Get the image
			BufferedImage bi = loadImage(url);
			String imgFileName = id + ".jpg";
			
			
			String filename = "";
			
			if (isWindows()) {
				filename = "C:/Users/r23ak/WatchImages/" + imgFileName; 
			} else if (isUnix()) {
				filename = "/opt/image/" + imgFileName;
			}
				
			File outputfile = new File(filename); 
			ImageIO.write(bi, "jpg", outputfile);
			result = new com.rstech.imagelib.ImageResult();
			result.setImage(bi);

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return result;

	}
	
	public static void main(String args[]) {
		String urlString =   "http://www.artinfo.com/media/image/81553/GerardAmbrosialDelight.jpg";
		
		try {
			ImageProcessing.fetchImage(urlString, "-22222");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
