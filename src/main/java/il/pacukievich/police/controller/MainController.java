package il.pacukievich.police.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

		@GetMapping("/admin/dashboard")
		public String adminDashboard() {
				return "admin-dashboard";
		}

		@GetMapping("/user/profile")
		public String userProfile() {
				return "user-profile";
		}

		@GetMapping("/public")
		public String publicPage() {
				return "public-page";
		}
		@GetMapping("/")
		public String mainPage() {
				return "main-page";
		}
}
