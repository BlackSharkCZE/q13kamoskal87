package cz.kamoska.partner.support;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 17.10.12
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */
public class PictureUtils {

	private static Logger logger = LoggerFactory.getLogger(PictureUtils.class);

	public static Image createImageFromBytes(byte[] bytes) {
		return Toolkit.getDefaultToolkit().createImage(bytes);
	}

	public static BufferedImage createFormInputStream(InputStream inputStream) {
		try {
			BufferedImage bi = ImageIO.read(inputStream);
			return bi;
		} catch (IOException e) {
			logger.error("Can not read InputStream to create BufferedImage. ", e);
			return null;
		}
	}

	public static boolean saveAsJPG(String file, BufferedImage bufferedImage) {
		try (
				FileOutputStream fo = new FileOutputStream(file);
				BufferedOutputStream os = new BufferedOutputStream(fo)
		) {
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
			int quality = 80;
			quality = Math.max(0, Math.min(quality, 100));
			param.setQuality((float) quality / 100.0f, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(bufferedImage);
			return true;
		} catch (Exception e) {
			logger.error("Can not save Image " + file, e);
		}
		return false;
	}

	public static BufferedImage resizeImage(BufferedImage img, int newW, int newH) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}

	/**
	 * Vyrizne nejvetsi ctverec ze zdrojoveho obrazku
	 * @param img zdrojovy obrazek
	 * @return nejvetsi mozny ctverec z obrazku
	 */
	public static BufferedImage cropImageByCenter(BufferedImage img) {

		int maxSize;
		int dx1;
		int dx2;
		int dy1;
		int dy2;

		if (img.getWidth() >= img.getHeight()) {
			// obrazek je dlouhy a uzky. Zakladem je tedy vyska
			maxSize = img.getHeight();
			dy1= 0;
			dy2 = maxSize;
			dx1 = (img.getWidth() / 2) - (maxSize / 2);
			dx2 = (img.getWidth() / 2) + (maxSize / 2);
		} else {
			// obrazek je hubeny a vysoky. Zakladem je tedy sirka
			maxSize = img.getWidth();
			dx1= 0;
			dx2 = maxSize;
			dy1 = (img.getHeight() / 2) - (maxSize / 2);
			dy2 = (img.getHeight() / 2) + (maxSize / 2);
		}

		BufferedImage bi = new BufferedImage(maxSize, maxSize, img.getType());
		Graphics2D g = bi.createGraphics();
		g.drawImage(img, 0,0,maxSize, maxSize, dx1, dy1, dx2, dy2, null);
		g.drawImage(img, 0,0,maxSize, maxSize, dx1, dy1, dx2, dy2, null);
		g.dispose();
		return bi;
	}

}
