package org.persistence.RESTui;

import java.io.IOException;
import java.util.Enumeration;
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

/**
 * Simple REST Interface, only GET for search in the database with JSON.
 */
@WebServlet("/REST2")
public class PersistenceWithEJBREST2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceWithEJBREST2.class);

	@EJB
	private PersonBean personBean;

	/** {@inheritDoc} */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			getPersonsAsJson(response, request);
		} catch (Exception e) {
			response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
			LOGGER.error("Persistence operation failed", e);
		}
	}

	private void getPersonsAsJson(HttpServletResponse response, HttpServletRequest request) throws IOException {
		
		
		
		String searchParam = null;
		
		
		Enumeration parameterList = request.getParameterNames();
		while (parameterList.hasMoreElements()) {
			String sName = parameterList.nextElement().toString();
			if (sName.equals((String) "lastName")) {
				String[] sMultiple = request.getParameterValues(sName);
				if (1 >= sMultiple.length)
					searchParam = sMultiple[0];
			}

		}
		List<Person> resultList2 = personBean.getFilterdPersons((String) searchParam);
		if (!resultList2.isEmpty()) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write((new Gson().toJson(resultList2)));
		}

		else {
			response.getWriter().write((new Gson().toJson("list is empty")));
		}
	}

	/** {@inheritDoc} */

	//

}
