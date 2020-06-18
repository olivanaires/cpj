package br.com.ota.cpjbackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "value=teste")
class CpjBackendApplicationTests {

	@Value("${value}")
	private String value;

	@Test
	void contextLoads() {
		assert(this.value).equals("teste");
	}

}
