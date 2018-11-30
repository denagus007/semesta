package co.id.app.aisa.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Testing {
	public static void main(String[] args) {
		PasswordEncoder asd  = new BCryptPasswordEncoder();
		System.out.print(asd.encode("aisaauthclient"));
	}
	
}
