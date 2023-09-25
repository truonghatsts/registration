package org.truonghatsts.registration.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.truonghatsts.registration.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
}
