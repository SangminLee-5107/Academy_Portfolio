package com.code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.code.springdemo.entity.Authorities;
import com.code.springdemo.entity.Users;
import com.code.springdemo.service.ArtshopService;

@Controller
public class LoginController {
	@Autowired
	private ArtshopService artshopservice;
	
	//로그인
	@GetMapping("/showMyLoginPage")
	public String showMyLoginpage() {
		return "fancy-login";
	}
	
	//로그아웃
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/";
	}
	
	//권한제한 페이지
	@GetMapping("/access-denied")
	public String showDenied(Authentication auth, Model theModel) {
		String username = auth.getName();
		String authority = artshopservice.getAuth(username);
		
		theModel.addAttribute("Role", authority);
		
		return "access-denied";
	}
	
	//기존 회원 정보 보여주기
	@GetMapping("/change-pw")
	public String change_pw(Authentication authentication, Model theModel) {
		String userName = authentication.getName();
		int theId = artshopservice.getIntUsers(userName);
		Users theUser = artshopservice.getUsers(theId);
		
		theModel.addAttribute("Users", theUser);
		
		return "change-pw";
	}
	
	//변경할 개인정보 수정하기
	@GetMapping("/changepw")
	public String changepw(Authentication authentication, @ModelAttribute("Users") Users theusers, Model theModel) {
		String userName = authentication.getName();
		int theId = artshopservice.getIntUsers(userName);
		artshopservice.updateUsers(theId, theusers);
		theModel.addAttribute("unregister","logout");
		
		return "home";
	}
	
	//일반유저 회원가입
	@GetMapping("/common_register")
	public String common_register() {
		return "common-register";
	} 
	
	//아티스트 회원가입
	@GetMapping("/artist_register")
	public String artist_register() {
		return "artist-register";
	}
	
	//회원가입 처리하기
	@GetMapping("/register")
	public String register(Users theuser, Authorities theauthorities, Model theModel) {
		
		artshopservice.register(theuser, theauthorities);
		
		return "redirect:/showMyLoginPage";
	}
	
	//회원 탈퇴하기
	@GetMapping("/unregister")
	public String unregister(Authentication authentication, Model theModel) {
		String userName = authentication.getName();
		int theId = artshopservice.getIntUsers(userName);
		System.out.println(userName);
		artshopservice.unregister(theId);
		theModel.addAttribute("unregister","logout");
		
		return "home";
	}
}