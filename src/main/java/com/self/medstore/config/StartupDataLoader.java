package com.self.medstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.self.medstore.entity.Admin;
import com.self.medstore.repository.AdminRepository;

@Configuration
public class StartupDataLoader {

	@Autowired
	private AdminRepository adminRepository;

	@Bean
	public CommandLineRunner loadAdmin() {
		return args -> {
			String userName = "divyansh@gmail.com";
			if (adminRepository.findByUserName(userName).isEmpty()) {
				Admin admin = new Admin();
				admin.setName("Divyansh");
				admin.setUserName(userName);
				admin.setPassword("divyansh123");
				adminRepository.save(admin);
				System.out.println("✅ Default Admin inserted: divyansh@gmail.com / divyansh123");
			} else {
				System.out.println("✅ Default Admin already exists");
			}
		};
	}
}
