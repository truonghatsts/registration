package org.truonghatsts.registration.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.truonghatsts.registration.dao.UserRepository;
import org.truonghatsts.registration.domain.UserEntity;

import java.util.Objects;

@Service
@AllArgsConstructor
@Log
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity merge(UserEntity newUser) throws RegistrationException {
        UserEntity existingUser = userRepository.findByEmail(newUser.getEmail());
        if (existingUser != null) {
            if (!Objects.equals(newUser.getId(), existingUser.getId())) {
                throw new RegistrationException("Email was used");
            }
            if(StringUtils.isNotBlank(newUser.getPassword())) {
                ensurePasswordComplexity(newUser.getPassword());
                newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            } else {
                newUser.setPassword(existingUser.getPassword());
            }
        } else {
            ensurePasswordComplexity(newUser.getPassword());
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        return userRepository.save(newUser);
    }

    private void ensurePasswordComplexity(String password) throws RegistrationException {
        if(StringUtils.isBlank(password)) {
            throw new RegistrationException("Password is blank");
        }
        if(password.length() < 5) {
            throw new RegistrationException("Password is too short");
        }
    }

    public UserEntity getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return userRepository.findByEmail(email);
    }
}
