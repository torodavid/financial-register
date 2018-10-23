package com.torodavid.thesis.financialregister.dal.repository;

import com.torodavid.thesis.financialregister.dal.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
    @Modifying
    @Query("UPDATE User U set U.email = ?2, U.password = ?3, U.surname = ?4, U.forename = ?5 where U.id = ?1")
    void setUserById(String id, String email, String password, String surname, String forename);
    @Query("SELECT CASE WHEN COUNT(U) > 0 THEN 'true' ELSE 'false' END FROM User U WHERE U.username = ?1")
    Boolean isUsernameExists(String username);
    @Query("SELECT CASE WHEN COUNT(U) > 0 THEN 'true' ELSE 'false' END FROM User U WHERE U.email = ?1")
    Boolean isEmailExists(String email);
}
