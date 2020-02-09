package com.github.bagiasn.bookspot.user.api;

import com.github.bagiasn.bookspot.user.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface UserRepository extends CrudRepository<User, Long> {

    @RestResource(path = "/byEmail", rel = "/byEmail")
    User findByEmail(@Param("email") String email);
}
