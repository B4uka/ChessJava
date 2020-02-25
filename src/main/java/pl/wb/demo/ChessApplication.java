package pl.wb.demo;

		import org.springframework.boot.SpringApplication;
		import org.springframework.boot.autoconfigure.SpringBootApplication;
		import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ChessApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ChessApplication.class, args);
	}
}

