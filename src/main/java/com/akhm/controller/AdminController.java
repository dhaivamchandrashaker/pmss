package com.akhm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.akhm.repository.entity.AdminTl;
import com.akhm.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AdminController {
	@Autowired
	private AdminService adminService;

	@GetMapping("/reg")
	public String showRegistration() {

		return "registration";
	}

	@PostMapping("/reg")
	public String registration(AdminTl adminTl) {
		log.info("In AdminController .. registration started");
		AdminTl _adminTl = adminService.saveAdmin(adminTl);
		log.info("In AdminController .. registration successfully completed");
		return "redirect:login";
	}

	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	@PostMapping("/log")
	public String login(AdminTl adminTl, HttpServletRequest request) {
		log.info("In AdminController .. login() started");
		AdminTl admin = adminService.getAdmin(adminTl.getEmailId(), adminTl.getPassword());
		System.out.println("user details are " + admin);
		log.info("In UserController .. login successfully completed");
		System.out.println(admin != null);
		if (admin != null) {

			HttpSession session = request.getSession();
			System.out.println("If");
			session.setAttribute("AUTH_ADMIN", admin);
			return "redirect:adminhome";
		} else {
			return "redirect:login?errorMessage=Invaid emailId or password";
		}

	}

	@GetMapping("/adminhome")
	public String userHome(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session != null) {

			AdminTl admin = (AdminTl) session.getAttribute("AUTH_ADMIN");
			System.out.println("AUTH_ADMIN" + admin);
			if (admin != null) {

				return "adminhome";
			}
		}
		return "redirect:login";
	}

	@GetMapping("/logout")
	public String logOut(HttpServletRequest request) {

		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:login";
	}

	@GetMapping("/changepassword")
	public String showChangePassword(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session != null) {
			AdminTl auth_admin = (AdminTl) session.getAttribute("AUTH_ADMIN");
			if (auth_admin != null) {
				return "changepassword";
			}
		}
		return "redirect:login";
	}

	@PostMapping("/changepassword")
	public String changePassword(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session != null) {
			AdminTl auth_admin = (AdminTl) session.getAttribute("AUTH_ADMIN");
			if (auth_admin != null) {
				String oldPassword = request.getParameter("oldPassword");
				String newPassword = request.getParameter("newPassword");
				String confirmPassword = request.getParameter("confirmPassword");
				if (oldPassword.equals(auth_admin.getPassword()) && newPassword.equals(confirmPassword)) {
					auth_admin.setPassword(newPassword);
					adminService.updateAdmin(auth_admin);
				}
				return "redirect:adminhome";
			}
		}
		return "redirect:login";

	}
	@GetMapping("/editprofile")
	public String showEditProfile(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session != null) {
			AdminTl auth_admin = (AdminTl) session.getAttribute("AUTH_ADMIN");
			if (auth_admin != null) {
				return "editprofile";
			}
		}
		return "redirect:login";
	}

	@PostMapping("/editprofile")
	public String updateProfile(HttpServletRequest request, AdminTl adminTl) {

		HttpSession session = request.getSession(false);
		if (session != null) {
			AdminTl auth_admin = (AdminTl) session.getAttribute("AUTH_ADMIN");
			if (auth_admin != null) {
				auth_admin.setFirstName(adminTl.getFirstName());
				auth_admin.setLastName(adminTl.getLastName());
				auth_admin.setEmailId(adminTl.getEmailId());
				auth_admin.setPassword(adminTl.getPassword());
				auth_admin.setMobileNumber(adminTl.getMobileNumber());
				auth_admin.setStatus(adminTl.getStatus());
				adminService.updateAdmin(auth_admin);
				return "redirect:editprofile";
			}
		}
		return "redirect:login";
	}
} 