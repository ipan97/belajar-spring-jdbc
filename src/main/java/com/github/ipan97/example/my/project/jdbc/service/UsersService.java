/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ipan97.example.my.project.jdbc.service;

import com.github.ipan97.example.my.project.domain.Address;
import com.github.ipan97.example.my.project.domain.Person;
import com.github.ipan97.example.my.project.domain.Users;
import com.github.ipan97.example.my.project.domain.UsersDetail;
import java.util.List;

/**
 *
 * @author acer
 */
public interface UsersService {
    Person getPersonById(Integer id);
    Person getPersonByNama(String nama);
    Person getPersonByAddress(Address address);
    Users getByUsersUsername(String username);
    List<UsersDetail> getAllByUsersDetail();
}
