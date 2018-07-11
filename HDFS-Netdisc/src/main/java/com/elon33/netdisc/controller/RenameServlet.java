package com.elon33.netdisc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.mapred.JobConf;

import com.elon33.netdisc.model.HdfsDAO;

/**
 * Servlet implementation class DeleteFileServlet
 */
public class RenameServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
		String newName = request.getParameter("newName");
		String currentPath = (String)session.getAttribute("currentPath");
		String filePath = new String(request.getParameter("filePath").getBytes("iso8859-1"), "utf-8");

		JobConf conf = HdfsDAO.config();
		HdfsDAO hdfs = new HdfsDAO(conf);
		hdfs.rename(filePath, currentPath+'/'+newName);
		// System.out.println("===="+filePath+"====");
		// FileStatus[] list = hdfs.ls("/user/root/");
		FileStatus[] list = hdfs.ls(currentPath);
		request.setAttribute("list", list);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
