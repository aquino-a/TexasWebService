/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.repository;

import com.aquino.TexasWebService.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author alex
 */
public interface UserRepository extends CrudRepository<User, Long>{
    User findByUsername(String username);
    User findByEmail(String email);
}
