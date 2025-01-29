package il.pacukievich.police.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Измените @RestController на @Controller
public class MainController {

		@GetMapping("/admin/dashboard")
		public String adminDashboard() {
				return "admin-dashboard"; // Указывает имя шаблона admin-dashboard.html
		}

		@GetMapping("/user/profile")
		public String userProfile() {
				return "user-profile"; // Указывает имя шаблона user-profile.html
		}

		@GetMapping("/public")
		public String publicPage() {
				return "public-page"; // Указывает имя шаблона public-page.html
		}
		@GetMapping("/")
		public String mainPage() {
				return "main-page"; // Указывает имя шаблона public-page.html
		}
}
