/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ipan97.example.my.project.jdbc.dao;

import com.github.ipan97.example.my.project.domain.Address;
import com.github.ipan97.example.my.project.domain.Person;
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
public class PersonDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    private static final String SQL_UPDATE
            = "update t_person set nama=:nama_person,tanggal_lahir=:tanggal_lahir_person,"
            + "id_address=:id_address_person,email=email_person,phone_number=:phone_number_person"
            + " where id=:id_person";
    private static final String SQL_BY_ID="select *from t_person where id=?";
    private static final String SQL_BY_NAMA="select *from t_person where nama=?";
    private static final String SQL_BY_ADDRESS="select *from t_person where id_address=?";
    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("t_person")
                .usingGeneratedKeyColumns("id");
    }
@Transactional
    public void simpan(Person p) {
        if (p.getId() == null) {
            SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(p);
            Number id = this.simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
            p.setId(id.intValue());
        } else {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                    .addValue("nama_person", p.getNama())
                    .addValue("tanggal_lahir_person", p.getTanggalLahir())
                    .addValue("id_address_person", p.getAddress().getId())
                    .addValue("email_person", p.getEmail())
                    .addValue("phone_number_person", p.getPhoneNumber());
            namedParameterJdbcTemplate.update(SQL_UPDATE, sqlParameterSource);
        }
    }

    public Person getById(Integer id) {
        return jdbcTemplate.queryForObject(SQL_BY_ID,new RowMapperPerson(),id);
    }

    public Person getByNama(String nama) {
        return jdbcTemplate.queryForObject(SQL_BY_NAMA,new RowMapperPerson(),nama);
    }

    public Person getByAddress(Address address) {
        return jdbcTemplate.queryForObject(SQL_BY_ADDRESS,new RowMapperPerson(),address.getId());
    }

    private class RowMapperPerson implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet rs, int i) throws SQLException {
            Person p=new Person();
            p.setId((Integer) rs.getObject("id"));
            p.setNama(rs.getString("nama"));
            p.setTanggalLahir(rs.getDate("tanggal_lahir"));
            p.setEmail(rs.getString("email"));
            p.setPhoneNumber(rs.getString("phone_number"));
            Address a=new Address();
            a.setId((Integer) rs.getObject("id"));
            a.setCity(rs.getString("city"));
            a.setStreet(rs.getString("street"));
            a.setPostalCode(rs.getString("postal_code"));
            p.setAddress(a);
            return p;
        }

    }
}
