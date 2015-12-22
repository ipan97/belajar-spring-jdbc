/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  acer
 * Created: Nov 25, 2015
 */

CREATE DATABASE my_training;
USE my_training;
CREATE TABLE t_address(
    id int(7) auto_increment,
    city VARCHAR(50),
    street VARCHAR(50),
    postal_code VARCHAR(50),
    PRIMARY KEY(id)
);
CREATE TABLE t_person(
    id int(7) auto_increment,
    nama VARCHAR(50),
    tanggal_lahir date,
    id_address int(7),
    email VARCHAR(25),
    phone_number VARCHAR(12),
    FOREIGN KEY fk_address(id_address)REFERENCES t_address(id),
    PRIMARY KEY(id)
);
CREATE TABLE t_users(
    id int(7)auto_increment,
    id_person int(7),
    username VARCHAR(50),
    password VARCHAR(50),
    FOREIGN KEY fk_person(id_person)REFERENCES t_person(id),
    PRIMARY KEY(id)
);
CREATE TABLE t_users_detail(
    id int(7)auto_increment,
    id_person int(7),
    id_users int(7),
    keterangan VARCHAR(50),
    FOREIGN KEY(id_person)REFERENCES t_person(id),
    FOREIGN KEY(id_users)REFERENCES t_users(id),
    PRIMARY KEY(id)
);