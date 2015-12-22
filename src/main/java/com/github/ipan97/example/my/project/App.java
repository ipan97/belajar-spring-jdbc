/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ipan97.example.my.project;

import com.github.ipan97.example.my.project.jdbc.dao.UsersDetailDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author acer
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:SpringConfig.xml");
        System.out.println("sukses membaca SpringConfig.xml");
        UsersDetailDao service=(UsersDetailDao) ctx.getBean("usersDetailDao");
        //output dengan java 8
        service.getAllUsersDetail().forEach((detail)->{
            System.out.println(detail.getId());
            System.out.println(detail.getIdUsers().getUsername());
            System.out.println(detail.getIdUsers().getPassword());
            System.out.println(detail.getIdPerson().getNama());
            System.out.println(detail.getIdPerson().getTanggalLahir());
            System.out.println(detail.getIdPerson().getTanggalLahir());
            System.out.println(detail.getIdPerson().getAddress().getStreet());
            System.out.println(detail.getIdPerson().getAddress().getCity());
            System.out.println(detail.getIdPerson().getPhoneNumber());
            System.out.println(detail.getIdPerson().getAddress().getPostalCode());
            System.out.println(detail.getKeterangan());
        });
        
    }
}
