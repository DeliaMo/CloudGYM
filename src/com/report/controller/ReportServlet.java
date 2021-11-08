package com.report.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coach.model.*;
import com.comment.model.*;
import com.google.gson.Gson;
import com.posts.model.*;
import com.report.model.*;
import com.reportRecord.model.*;
import com.user.model.*;
import com.video.model.*;

import java.util.*;

public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReportServlet() {
		super();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("addreport".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			PrintWriter out = res.getWriter();
			res.setCharacterEncoding("UTF-8");

			try {
				Integer userID = (Integer) req.getSession().getAttribute("userID");

				String videoIDstr = req.getParameter("videoID");
				Integer videoID = null;
				if (videoIDstr != null) {
					videoID = Integer.parseInt(videoIDstr);
				}

				String postsIDstr = req.getParameter("postsID");
				Integer postsID = null;
				if (postsIDstr != null) {
					postsID = Integer.parseInt(postsIDstr);
				}

				String commentIDstr = req.getParameter("commentID");
				Integer commentID = null;
				if (commentIDstr != null) {
					commentID = Integer.parseInt(commentIDstr);
				}

				ReportService reportSvc = new ReportService();
				ReportRecordService reportrecordSvc = new ReportRecordService();
				List<ReportRecordVO> recordlist = reportrecordSvc.getAll();
				List<Integer> items = new ArrayList<Integer>();
				for (ReportRecordVO vo : recordlist) {
					Integer itemID = vo.getItemID();
					items.add(itemID);
				}
				if (videoIDstr != null && videoIDstr.trim().length() != 0) {
					reportSvc.addReport(userID, videoID);
					Integer authorID = new VideoService().findByPrimaryKey(videoID).getUserID();
					if (!items.contains(videoID)) {
						reportrecordSvc.addReportRecord(videoID, authorID);
					}
					
					VideoService videoSvc = new VideoService();
					VideoVO videoVO = videoSvc.findByPrimaryKey(videoID);
					Integer videoReportedTimes = videoVO.getReportedTimes();
					videoReportedTimes++;
					videoSvc.updateReportedTimes(videoReportedTimes, videoID);
					if(authorID.toString().startsWith("1")) {
						UserVO userVO = new UserService().findByUserId(authorID);
						Integer reportedTimes = userVO.getUserReportedTimes();
						reportedTimes++;
						userVO.setUserReportedTimes(reportedTimes);
						new UserJDBCDAO().update(userVO);
					}
					if(authorID.toString().startsWith("2")) {
						CoachVO coachVO = new CoachService().getByUserID(authorID);
						Integer reportedTimes = coachVO.getReportedTimes();
						reportedTimes++;
						coachVO.setReportedTimes(reportedTimes);
						new CoachJDBCDAO().update(coachVO);
					}
				}
				if (postsIDstr != null && postsIDstr.trim().length() != 0) {
					reportSvc.addReport(userID, postsID);
					Integer authorID = new PostsService().getByPostsID(postsID).getUserID();
					if (!items.contains(postsID)) {
						reportrecordSvc.addReportRecord(postsID, authorID);
					}
					
					PostsService postsSvc = new PostsService();
					PostsVO postsVO = postsSvc.getByPostsID(postsID);
					Integer postsReportedTimes = postsVO.getPostsReportedTimes();
					postsReportedTimes++;
					postsSvc.updatePostsReportedTimes(postsReportedTimes, postsID);
					if(authorID.toString().startsWith("1")) {
						UserVO userVO = new UserService().findByUserId(authorID);
						Integer reportedTimes = userVO.getUserReportedTimes();
						reportedTimes++;
						userVO.setUserReportedTimes(reportedTimes);
						new UserJDBCDAO().update(userVO);
					}
					if(authorID.toString().startsWith("2")) {
						CoachVO coachVO = new CoachService().getByUserID(authorID);
						Integer reportedTimes = coachVO.getReportedTimes();
						reportedTimes++;
						coachVO.setReportedTimes(reportedTimes);
						new CoachJDBCDAO().update(coachVO);
					}
				}
				if (commentIDstr != null && commentIDstr.trim().length() != 0) {
					reportSvc.addReport(userID, commentID);
					Integer authorID = new CommentService().getByCommtntID(commentID).getUserID();
					if (!items.contains(commentID)) {
						reportrecordSvc.addReportRecord(commentID, authorID);
					}
					if(authorID.toString().startsWith("1")) {
						UserVO userVO = new UserService().findByUserId(authorID);
						Integer reportedTimes = userVO.getUserReportedTimes();
						reportedTimes++;
						userVO.setUserReportedTimes(reportedTimes);
						new UserJDBCDAO().update(userVO);
					}
					if(authorID.toString().startsWith("2")) {
						CoachVO coachVO = new CoachService().getByUserID(authorID);
						Integer reportedTimes = coachVO.getReportedTimes();
						reportedTimes++;
						coachVO.setReportedTimes(reportedTimes);
						new CoachJDBCDAO().update(coachVO);
					}
				}

				List<String> list = new ArrayList<String>();
				list.add("reportSuccess");
				String json = new Gson().toJson(list);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				out.write(json);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}

		if ("deleteReport".equals(action)) {

			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);

			PrintWriter out = res.getWriter();
			try {
				Integer userID = (Integer) req.getSession().getAttribute("userID");
				
				String videoIDstr = req.getParameter("videoID");
				Integer videoID = null;
				if(videoIDstr != null) {
					videoID = Integer.parseInt(videoIDstr);
				}
				
				String postsIDstr = req.getParameter("postsID");
				Integer postsID = null;
				if (postsIDstr != null) {
					postsID = Integer.parseInt(postsIDstr);
				}
				
				String commentIDstr = req.getParameter("commentID");
				Integer commentID = null;
				if(commentIDstr != null) {
					commentID = Integer.parseInt(commentIDstr);
				}
				
				
				ReportService reportSvc = new ReportService();
				ReportRecordService reportrecordSvc = new ReportRecordService();
				if(videoIDstr != null && videoIDstr.trim().length() != 0) {
					reportSvc.deleteReport(userID, videoID);
					reportrecordSvc.deleteReportRecord(videoID);
					Integer authorID = new VideoService().findByPrimaryKey(videoID).getUserID();
					
					VideoService videoSvc = new VideoService();
					VideoVO videoVO = videoSvc.findByPrimaryKey(videoID);
					Integer videoReportedTimes = videoVO.getReportedTimes();
					videoReportedTimes--;
					videoSvc.updateReportedTimes(videoReportedTimes, videoID);
					if(authorID.toString().startsWith("1")) {
						UserVO userVO = new UserService().findByUserId(authorID);
						Integer reportedTimes = userVO.getUserReportedTimes();
						reportedTimes--;
						userVO.setUserReportedTimes(reportedTimes);
						new UserJDBCDAO().update(userVO);
					}
					if(authorID.toString().startsWith("2")) {
						CoachVO coachVO = new CoachService().getByUserID(authorID);
						Integer reportedTimes = coachVO.getReportedTimes();
						reportedTimes--;
						coachVO.setReportedTimes(reportedTimes);
						new CoachJDBCDAO().update(coachVO);
					}
				}
				if(postsIDstr != null && postsIDstr.trim().length() != 0) {
					reportSvc.deleteReport(userID, postsID);
					reportrecordSvc.deleteReportRecord(postsID);
					Integer authorID = new PostsService().getByPostsID(postsID).getUserID();
					
					PostsService postsSvc = new PostsService();
					PostsVO postsVO = postsSvc.getByPostsID(postsID);
					Integer postsReportedTimes = postsVO.getPostsReportedTimes();
					postsReportedTimes--;
					postsSvc.updatePostsReportedTimes(postsReportedTimes, postsID);
					if(authorID.toString().startsWith("1")) {
						UserVO userVO = new UserService().findByUserId(authorID);
						Integer reportedTimes = userVO.getUserReportedTimes();
						reportedTimes--;
						userVO.setUserReportedTimes(reportedTimes);
						new UserJDBCDAO().update(userVO);
					}
					if(authorID.toString().startsWith("2")) {
						CoachVO coachVO = new CoachService().getByUserID(authorID);
						Integer reportedTimes = coachVO.getReportedTimes();
						reportedTimes--;
						coachVO.setReportedTimes(reportedTimes);
						new CoachJDBCDAO().update(coachVO);
					}
				}
				if(commentIDstr != null && commentIDstr.trim().length() != 0) {
					reportSvc.deleteReport(userID, commentID);
					reportrecordSvc.deleteReportRecord(commentID);
					Integer authorID = new CommentService().getByCommtntID(commentID).getUserID();
					if(authorID.toString().startsWith("1")) {
						UserVO userVO = new UserService().findByUserId(authorID);
						Integer reportedTimes = userVO.getUserReportedTimes();
						reportedTimes--;
						userVO.setUserReportedTimes(reportedTimes);
						new UserJDBCDAO().update(userVO);
					}
					if(authorID.toString().startsWith("2")) {
						CoachVO coachVO = new CoachService().getByUserID(authorID);
						Integer reportedTimes = coachVO.getReportedTimes();
						reportedTimes--;
						coachVO.setReportedTimes(reportedTimes);
						new CoachJDBCDAO().update(coachVO);
					}
				}
				
				List<String> list = new ArrayList<String>();
				list.add("cancelSuccess");
				String json = new Gson().toJson(list);
				res.setContentType("application/json");
				res.setCharacterEncoding("UTF-8");
				out.write(json);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

}
