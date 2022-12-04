package ru.kaznacheev.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kaznacheev.library.dao.book.BooksDAO;
import ru.kaznacheev.library.dao.person.PeopleDAO;
import ru.kaznacheev.library.model.Book;
import ru.kaznacheev.library.model.Person;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private BooksDAO booksDAO;
    private PeopleDAO peopleDAO;

    public BooksController(BooksDAO booksDAO, PeopleDAO peopleDAO) {
        this.booksDAO = booksDAO;
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksDAO.getAll());
        return "book/books";
    }

    @GetMapping("/new")
    public String getBookNew() {
        return "book/new";
    }

    @PostMapping()
    public String postBook(@ModelAttribute("book") Book book) {
        booksDAO.add(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String getBookId(@PathVariable("id") int id, Model model) {
        Book book = booksDAO.get(id);
        model.addAttribute("book", book);
        Person owner = booksDAO.checkBookOwner(id);
        if (owner == null) {
            List<Person> people = peopleDAO.getAll();
            model.addAttribute("people", people);
        } else {
            model.addAttribute("owner", owner);
        }
        return "book/book";
    }

    @GetMapping("/{id}/edit")
    public String GetBookIdEdit(@PathVariable("id") int id, Model model) {
        Book book = booksDAO.get(id);
        model.addAttribute("book", book);
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String patchBookId(@ModelAttribute("book") Book book) {
        booksDAO.update(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBookId(@PathVariable("id") int id) {
        booksDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String patchBookIdAssign(@PathVariable("id") int bookId, @RequestParam("personSelect") int personId) {
        booksDAO.assignBook(bookId, personId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{id}/release")
    public String patchBookIdRelease(@PathVariable("id") int bookId) {
        booksDAO.releaseBook(bookId);
        return "redirect:/books/" + bookId;
    }

}
