package com.github.bagiasn.bookspot.user;

import com.github.bagiasn.bookspot.user.models.Credentials;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkInputErrorReturnsBadRequest() throws Exception {
        // Use an empty credentials object.
        Credentials credentials = new Credentials();
        // Expect a - bad request - response.
        this.mockMvc.perform(post("/user/logout", credentials)).andExpect(status().isBadRequest());
    }
}
