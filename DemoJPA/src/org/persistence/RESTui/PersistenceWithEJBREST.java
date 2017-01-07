package org.persistence.RESTui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.persistence.Person;
import org.persistence.PersonBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.*;

/**
 * Simple REST Interface, only GET and POST with JSON.
 */
@WebServlet("/REST")
public class PersistenceWithEJBREST extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceWithEJBREST.class);

	@EJB
	private PersonBean personBean;

	/** {@inheritDoc} */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			getAllPersonsAsJson(response);
		} catch (Exception e) {
			response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
			LOGGER.error("Persistence operation failed", e);
		}
	}


	private void getAllPersonsAsJson(HttpServletResponse response) throws IOException {
		List<Person> resultList = personBean.getAllPersons();
		if (!resultList.isEmpty()) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write((new Gson().toJson(resultList)));
		}

		else {
			response.getWriter().write((new Gson().toJson("list is empty")));
		}

	}

	/** {@inheritDoc} */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			setPersonsAsJson(request);
			response.getWriter().println("added");
		} catch (Exception e) {
			response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
			LOGGER.error("Persistence operation failed", e);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setPersonsAsJson(HttpServletRequest request) throws Throwable, IOException {

		JsonParser jsonParser = new JsonParser();

		// JSON darf keine [ haben sonst ist es ein Array. Nur {...}

		JsonObject jobj = (JsonObject) jsonParser.parse(getBody(request));
		Person person = new Person();
		person.setFirstName(jobj.get("firstName").toString().trim());
		person.setLastName(jobj.get("lastName").toString().trim());
		person.setEMail(jobj.get("eMail").toString().trim());
		person.setHomeTown(jobj.get("homeTown").toString().trim());

		personBean.addPerson(person);

	}

	// Liefert den Body des POST Requests als String
	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		return body;
	}

}
