package ru.kaznacheev.library.dao.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kaznacheev.library.dao.DAO;
import ru.kaznacheev.library.dao.book.BookRawMapper;
import ru.kaznacheev.library.model.Book;
import ru.kaznacheev.library.model.Person;

import java.util.List;

@Component
public class PeopleDAO implements DAO<Person> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> getAll() {
        String SQL = "SELECT * FROM Person;";
        return jdbcTemplate.query(SQL, new PersonRawMapper());
    }

    @Override
    public void add(Person object) {
        String SQL = "INSERT INTO Person (FIO, year) VALUES (?, ?);";
        jdbcTemplate.update(SQL, object.getFIO(), object.getYear());
    }

    @Override
    public Person get(int id) {
        String SQL = "SELECT * FROM Person P WHERE P.id = ?;";
        return jdbcTemplate.query(SQL, new Object[]{id}, new PersonRawMapper()).stream().findAny().orElse(null);
    }

    @Override
    public void update(Person object) {
        String SQL = "UPDATE Person SET FIO = ?, year = ? WHERE id = ?;";
        jdbcTemplate.update(SQL, object.getFIO(), object.getYear(), object.getId());
    }

    @Override
    public void delete(int id) {
        String SQL = "DELETE FROM Person P WHERE P.id = ?;";
        jdbcTemplate.update(SQL, id);
    }

    public List<Book> getBooksInfo(int id) {
        String SQL = "SELECT B.* FROM Book B JOIN Person P on P.id = B.personId WHERE P.id = ?;";
        return jdbcTemplate.query(SQL, new Object[]{id}, new BookRawMapper());
    }
}
