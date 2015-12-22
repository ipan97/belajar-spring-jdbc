/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ipan97.example.my.project.jdbc.dao;

import com.github.ipan97.example.my.project.domain.Address;
import com.github.ipan97.example.my.project.domain.Person;
import com.github.ipan97.example.my.project.domain.Users;
import com.github.ipan97.example.my.project.domain.UsersDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author acer
 */
@Repository
@Component
public class UsersDetailDao {
    
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final String SQL_UPDATE = "update t_users_detail set id_person=:person,id_users=:users where id=id_users_detail";
    private static final String SQL_TAMPIL_USER_DETAILS
            = "select ud.id,u.username,u.password,p.nama,p.tanggal_lahir,a.street,p.phone_number,a.city,a.postal_code,ud.keterangan "
            + "from t_users_detail ud,t_person p,t_users u,t_address a "
            + "where ud.id=u.id and p.id=u.id";

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("t_users_detail")
                .usingGeneratedKeyColumns("id");
    }
    
    public List<UsersDetail> getAllUsersDetail() {
        return jdbcTemplate.query(SQL_TAMPIL_USER_DETAILS,new RowMapperUsersDetail());
    }
    
    @Transactional
    void simpan(UsersDetail u) {
        if (u.getId() == null) {
            SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(u);
            Number id = this.simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
            u.setId(id.intValue());
        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("person", u.getIdPerson().getId())
                    .addValue("user", u.getIdUsers().getId());
            namedParameterJdbcTemplate.update(SQL_UPDATE, sqlParameterSource);
        }
        
    }

    private class RowMapperUsersDetail implements RowMapper<UsersDetail> {
        
        @Override
        public UsersDetail mapRow(ResultSet rs, int i) throws SQLException {
            UsersDetail u = new UsersDetail();
            u.setId((Integer) rs.getObject("id"));
            u.setKeterangan(rs.getString("keterangan"));
            Users us = new Users();
            us.setId((Integer) rs.getObject("id"));
            us.setUsername(rs.getString("username"));
            us.setPassword(rs.getString("password"));
            Person p = new Person();
            u.setIdUsers(us);
            p.setId((Integer) rs.getObject("id"));
            p.setNama(rs.getString("nama"));
            p.setTanggalLahir(rs.getDate("tanggal_lahir"));
            p.setPhoneNumber(rs.getString("phone_number"));
            Address a = new Address();
            a.setId((Integer) rs.getObject("id"));
            a.setStreet(rs.getString("street"));
            a.setCity(rs.getString("city"));
            a.setPostalCode(rs.getString("postal_code"));
            p.setAddress(a);
            u.setIdPerson(p);
            return u;
        }
        
    }
}
