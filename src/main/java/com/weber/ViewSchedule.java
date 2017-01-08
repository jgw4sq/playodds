package com.weber;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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
		if(((User)request.getSession().getAttribute("user"))==null){
			response.sendRedirect(request.getContextPath()+"/Login");

		}else{
			Date date = new Date();
			request.setAttribute("year", (date.getYear()+1900));
			request.setAttribute("month", (date.getMonth()+1));
			request.setAttribute("day", date.getDate());
			request.setAttribute("year2", (date.getYear()+1900));
			request.setAttribute("month2", (date.getMonth()+2));
			request.setAttribute("day2", (date.getDate()));
		//	request.setAttribute("year", arg1);
			doPost(request,response);
		//request.getRequestDispatcher("viewschedule.jsp").forward(request, response);
		
	}}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pool = ((User)request.getSession().getAttribute("user")).getPool();
		int year = (Integer) request.getAttribute("year");
		int month = (Integer) request.getAttribute("month");
		int day = (Integer) request.getAttribute("day");
		Timestamp startDate = new Timestamp(0,0,0,0,0,0,0);
		startDate.setYear((year)-1900);
		startDate.setMonth((month)-1);
		startDate.setDate((day));
		
	
		int year2 = (Integer) request.getAttribute("year2");
		int month2 = (Integer) request.getAttribute("month2");
		int day2 = (Integer) request.getAttribute("day2");
		Timestamp endDate = new Timestamp(0,0,0,0,0,0,0);
		endDate.setYear(year2-1900);
		endDate.setMonth((month2)-1);
		endDate.setDate((day2)+1);
		System.out.println(startDate.toString());
		System.out.println(endDate.toString());
		ArrayList<Shift> shifts = MakeSchedule.populateAllShifts(pool, startDate, endDate);
		request.setAttribute("shifts", shifts);
		request.getRequestDispatcher("viewschedule2.jsp").forward(request, response);
		
	}

}
