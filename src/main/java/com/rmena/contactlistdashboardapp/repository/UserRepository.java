package com.rmena.contactlistdashboardapp.user;

import com.rmena.contactlistdashboardapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
