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
import com.github.ipan97.example.my.project.jdbc.dao.PersonDao;
import com.github.ipan97.example.my.project.jdbc.dao.UsersDao;
import com.github.ipan97.example.my.project.jdbc.dao.UsersDetailDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author acer
 */
@Transactional
@Repository()
public class UsersServiceImpl implements UsersService {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private UsersDetailDao usersDetailDao;

    @Override
    public Person getPersonById(Integer id) {
        return personDao.getById(id);
    }

    @Override
    public Person getPersonByNama(String nama) {
        return personDao.getByNama(nama);
    }

    @Override
    public Person getPersonByAddress(Address address) {
        return personDao.getByAddress(address);
    }

    @Override
    public Users getByUsersUsername(String username) {
        return usersDao.getByUsername(username);
    }

    @Override
    public List<UsersDetail> getAllByUsersDetail() {
        return usersDetailDao.getAllUsersDetail();
    }

}
