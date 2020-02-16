package com.github.bagiasn.bookspot.user.api;

import com.github.bagiasn.bookspot.common.utils.HashGenerator;
import com.github.bagiasn.bookspot.user.models.Credentials;
import com.github.bagiasn.bookspot.user.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    private static final int UserTokenTtl = 7200;

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
                    Credentials creds = new Credentials();
                    creds.setEmail(providedEmail);
                    creds.setToken(token);

                    return ResponseEntity.ok().body(creds);
                } else {
                    logger.info("Wrong password was provided.");

                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody User user) {
        logger.info("Sign-up request received");

        if (user != null) {
            try {
                // Store an MD5 hash instead of plain text.
                user.setPassword(HashGenerator.GetPasswordHash(user.getPassword()));
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (DataIntegrityViolationException dex) {
                logger.warn("Postgres exception: {}", dex.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } catch (Exception ex) {
                logger.error("Sign up failed", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(@RequestBody Credentials credentials) {
        logger.info("Logout request received");

        String email = credentials.getEmail();
        String token = credentials.getToken();

        if (email != null && !email.isEmpty() && token != null && !token.isEmpty()) {
            try {
                String redisToken = redisTemplate.opsForValue().get(credentials.getEmail());
                if (redisToken == null) {
                    // It's safe to assume that the user session expired, so indicate success.
                    return ResponseEntity.ok().build();
                }
                // Check if the received token matches the one in redis.
                if (redisToken.equals(token)) {
                    logger.info("Logging out user {}", email);
                    redisTemplate.opsForValue().getOperations().delete(email);

                    return ResponseEntity.ok().build();
                }
                else {
                    return ResponseEntity.badRequest().build();
                }
            } catch (Exception ex) {
                logger.error("Logout failed", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
