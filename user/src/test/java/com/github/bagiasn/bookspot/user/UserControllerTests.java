package com.github.bagiasn.bookspot.user;

import com.github.bagiasn.bookspot.user.api.UserController;
import com.github.bagiasn.bookspot.user.api.UserRepository;
import com.github.bagiasn.bookspot.user.models.Credentials;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private StringRedisTemplate redisTemplate;

    @Test
    public void checkInputErrorReturnsBadRequest() throws Exception {
        // Use an empty credentials object.
        Credentials credentials = new Credentials();
        // Expect a - bad request - response.
        this.mockMvc.perform(post("/user/logout", credentials)).andExpect(status().isBadRequest());
    }
}
