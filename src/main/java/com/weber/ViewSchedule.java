package com.weber;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewSchedule
 */
@WebServlet("/ViewSchedule")
public class ViewSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewSchedule() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("viewschedule.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pool = ((User)request.getSession().getAttribute("user")).getPool();
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		Timestamp startDate = new Timestamp(0,0,0,0,0,0,0);
		startDate.setYear(Integer.parseInt(year)-1900);
		startDate.setMonth(Integer.parseInt(month)-1);
		startDate.setDate(Integer.parseInt(day));
		
	
		String year2 = request.getParameter("year2");
		String month2 = request.getParameter("month2");
		String day2 = request.getParameter("day2");
		Timestamp endDate = new Timestamp(0,0,0,0,0,0,0);
		endDate.setYear(Integer.parseInt(year2)-1900);
		endDate.setMonth(Integer.parseInt(month2)-1);
		endDate.setDate(Integer.parseInt(day2));
		System.out.println(startDate.toString());
		System.out.println(endDate.toString());
		ArrayList<Shift> shifts = MakeSchedule.populateAllShifts(pool, startDate, endDate);
		request.setAttribute("shifts", shifts);
		request.getRequestDispatcher("viewschedule2.jsp").forward(request, response);
		
	}

}
