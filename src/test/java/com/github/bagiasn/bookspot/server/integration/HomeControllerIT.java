package com.github.bagiasn.bookspot.server.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerIT {

	@LocalServerPort
	private int port;

	private URL url;

	@Autowired
	private TestRestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.url = new URL("http://localhost:" + port + "/");
	}

	@Test
	public void getHomeResponse() {
		ResponseEntity<String> response = template.getForEntity(url.toString(),
				String.class);
		assertTrue(response.getStatusCodeValue() == 200);
	}

}
