package cz.kamoska.partner.servlets;

import cz.kamoska.partner.config.MainConfig;
import cz.kamoska.partner.dao.interfaces.PictureDaoInterface;
import cz.kamoska.partner.entities.PictureEntity;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.10.12
 * Time: 21:13
 * Servlet pouzivany pro nacteni a navraceni jednotlivych obrazku z reklamy. Obrazky jsou uploadnute nekde v systemu, nejsou soucasti deploynute aplikace
 */
@WebServlet(name = "PictureProviderServlet", urlPatterns = "/images/*")
public class PictureProviderServlet extends HttpServlet {

	private final Logger logger = Logger.getLogger(PictureProviderServlet.class);
	
	@EJB
	private PictureDaoInterface pictureDaoInterface;

	private static final int BATCH_SIZE = 4 * 1024;

	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String url[] = request.getRequestURI().split("/");
		if (url != null && url.length > 1) {
			final String pictureStrId = url[url.length - 1];

			Integer pictureId =  null ;
			try {
				pictureId = Integer.parseInt(pictureStrId);
			} catch (NumberFormatException e) {
				logger.error("Invalid picture ID: " + pictureStrId);
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}

			if (pictureId != null) {
				PictureEntity pictureEntity = pictureDaoInterface.findByPrimaryKey(PictureEntity.class, pictureId);
				if (pictureEntity == null) {
					logger.info("Can not find pictureEntity for ID: " + pictureId);
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				} else {
					final String path = MainConfig.IMAGE_STORE_ROOT_PATH + "/" + pictureEntity.getPartnerEntity().getId()+ "/"+pictureEntity.getSystemName();
					File f = new File(path);
					if (f.exists()) {
						response.setContentType("image/jpeg");
						FileInputStream fSteam = new FileInputStream(f);
						OutputStream ostr = response.getOutputStream();
						byte[] cache = new byte[BATCH_SIZE];
						int read = 0;
						while ((read = fSteam.read(cache)) != -1) {
							ostr.write(cache,0,read);
						}
						ostr.flush();
					} else {
						logger.error("Can not find picture "+ path);
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					}
				}
			}

		} else {
			logger.error("Can not return picture with missing ID in URL");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
}
