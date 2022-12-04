package ru.kaznacheev.library.dao.person;

import org.springframework.jdbc.core.RowMapper;
import ru.kaznacheev.library.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRawMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setFIO(resultSet.getString("FIO"));
        person.setYear(resultSet.getInt("year"));
        return person;
    }
}
