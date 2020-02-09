package com.github.bagiasn.bookspot.user.api;

import com.github.bagiasn.bookspot.common.utils.HashGenerator;
import com.github.bagiasn.bookspot.user.models.Credentials;
import com.github.bagiasn.bookspot.user.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final int UserTokenTtl = 720;

    private  UserRepository userRepository;
    private  StringRedisTemplate redisTemplate;

    @Autowired
    public UserController(UserRepository userRepository, StringRedisTemplate redisTemplate) {
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Credentials credentials) {

        String providedEmail = credentials.getEmail();
        logger.info("Login request received: {}", providedEmail);

        String userToken = redisTemplate.opsForValue().get(providedEmail);
        if (userToken != null && !userToken.isEmpty()) {
            logger.info("User already logged-in");

            return ResponseEntity.ok().body("User is already logged-in");
        } else {
            // First, find the requested user.
            User user = userRepository.findByEmail(providedEmail);
            if (user == null) {
                logger.warn("Could not find user with email {}", providedEmail);

                return ResponseEntity.notFound().build();
            } else {
                // Check if the password is correct.
                String providedPassword = HashGenerator.GetPasswordHash(credentials.getPassword());
                if (providedPassword.equals(user.getPassword())) {
                    logger.info("Password is correct.");
                    // Generate a token
                    String token = UUID.randomUUID().toString();
                    // Update redis.
                    redisTemplate.opsForValue().set(providedEmail, token, Duration.ofSeconds(UserTokenTtl));

                    return ResponseEntity.ok().body(token);
                } else {
                    logger.info("Wrong password was provided.");

                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
        }
    }
}
