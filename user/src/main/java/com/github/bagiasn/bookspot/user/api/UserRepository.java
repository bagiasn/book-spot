package com.github.bagiasn.bookspot.user.api;

import com.github.bagiasn.bookspot.user.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
