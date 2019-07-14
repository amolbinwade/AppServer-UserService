package com.appserver.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.appserver.userservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
