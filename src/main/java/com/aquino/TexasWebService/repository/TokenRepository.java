/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.repository;

import com.aquino.TexasWebService.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author b005
 */
public interface TokenRepository extends CrudRepository<VerificationToken, Long>{
    public VerificationToken findByTokenValue(String tokenValue);
}
