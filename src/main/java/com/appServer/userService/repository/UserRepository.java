package com.appServer.userService.repository;

import org.springframework.data.repository.CrudRepository;

import com.appServer.userService.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
