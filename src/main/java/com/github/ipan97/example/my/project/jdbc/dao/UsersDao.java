/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ipan97.example.my.project.jdbc.dao;

import com.github.ipan97.example.my.project.domain.Users;
import com.github.ipan97.example.my.project.domain.UsersDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author acer
 */
@Repository
public class UsersDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final String SQL_BY_USERNAME = "select *from t_users where username=?";
    private static final String SQL_UPDATE 
            = "update t_users set username=:user,password=:pass where id=:id_users";
    @Autowired
    private UsersDetailDao usersDetailDao;
    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert=new SimpleJdbcInsert(dataSource)
                .withTableName("t_users")
                .usingGeneratedKeyColumns("id");
    }
    @Transactional
    public void simpan(Users u){
        SqlParameterSource sqlParameterSource=new BeanPropertySqlParameterSource(u);
        Number id=simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        u.setId(id.intValue());
        for(UsersDetail detail:u.getDetailUser()){
            detail.setIdUsers(u);
            usersDetailDao.simpan(detail);
        }
        if(u.getId()==null){
            SqlParameterSource sqlParameterSource1=new MapSqlParameterSource()
                    .addValue("user", u.getUsername())
                    .addValue("pass", u.getPassword());
            namedParameterJdbcTemplate.update(SQL_UPDATE,sqlParameterSource);
        }
    }
    public Users getByUsername(String username) {
        return jdbcTemplate.queryForObject(SQL_BY_USERNAME, new RowMapperUsers(), username);
    }

    private class RowMapperUsers implements RowMapper<Users> {

        @Override
        public Users mapRow(ResultSet rs, int i) throws SQLException {
            Users u = new Users();
            u.setId((Integer) rs.getObject("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            return u;
        }

    }
}
