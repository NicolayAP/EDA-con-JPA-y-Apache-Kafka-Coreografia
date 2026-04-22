package co.edu.uptc.shippingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ShippingserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShippingserviceApplication.class, args);
	}

}
