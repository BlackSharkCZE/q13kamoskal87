package cz.kamoska.partner.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.10.12
 * Time: 21:13
 * Servlet pouzivany pro nacteni a navraceni jednotlivych obrazku z reklamy. Obrazky jsou uploadnute nekde v systemu, nejsou soucasti deploynute aplikace
 */
@WebServlet(name = "PictureProviderServlet")
public class PictureProviderServlet extends HttpServlet {

	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//todo implement body, or set alternate_docroot for images, or configure nginx to provide pictures
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
}
