package ru.kaznacheev.library.dao.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kaznacheev.library.dao.DAO;
import ru.kaznacheev.library.model.Book;
import ru.kaznacheev.library.model.Person;

import java.util.List;

@Component
public class BooksDAO implements DAO<Book> {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> getAll() {
        String SQL = "SELECT * FROM Book;";
        return jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public void add(Book object) {
        String SQL = "INSERT INTO Book (title, author, year) VALUES (?, ?, ?);";
        jdbcTemplate.update(SQL, object.getTitle(), object.getAuthor(), object.getYear());
    }

    @Override
    public Book get(int id) {
        String SQL = "SELECT * FROM Book B WHERE B.id = ?;";
        return jdbcTemplate.query(SQL, new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(Book object) {
        String SQL = "UPDATE Book SET title = ?, author = ?, year = ? WHERE id = ?;";
        jdbcTemplate.update(SQL, object.getTitle(), object.getAuthor(), object.getYear(), object.getId());
    }

    @Override
    public void delete(int id) {
        String SQL = "DELETE FROM Book B WHERE B.id = ?;";
        jdbcTemplate.update(SQL, id);
    }


    public Person checkBookOwner(int bookId) {
        String SQL = "SELECT P.* FROM Person P JOIN Book B ON B.personId = P.id WHERE B.id = ?;";
        return jdbcTemplate.query(SQL, new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void assignBook(int bookId, int personId) {
        String SQL = "UPDATE Book SET personId = ? WHERE id = ?;";
        jdbcTemplate.update(SQL, personId, bookId);
    }

    public void releaseBook(int bookId) {
        String SQL = "UPDATE Book SET personId = null WHERE id = ?;";
        jdbcTemplate.update(SQL, bookId);
    }

}
