package ru.kaznacheev.library.dao.book;

import org.springframework.jdbc.core.RowMapper;
import ru.kaznacheev.library.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRawMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setYear(resultSet.getInt("year"));
        return book;
    }
}
