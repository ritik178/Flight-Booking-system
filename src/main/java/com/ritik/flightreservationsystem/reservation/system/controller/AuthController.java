package com.ritik.flightreservationsystem.reservation.system.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ritik.flightreservationsystem.reservation.system.model.User;
import com.ritik.flightreservationsystem.reservation.system.repository.UserRepository;
import com.ritik.flightreservationsystem.reservation.system.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final AuthenticationManager authManager;
	
	
	public AuthController(UserRepository userRepository,
			PasswordEncoder passwordEncoder,JwtUtil jwtUtil,
			AuthenticationManager authManager) {
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}
	
	public static Logger log= LoggerFactory.getLogger(AuthController.class);
	@PostMapping("/register")
	public String register(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		log.info("user registerd successfully");
		return "user registered succesfully";
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsername(),
						user.getPassword()));
		return jwtUtil.genrateToken(user.getUsername());
	}
}
