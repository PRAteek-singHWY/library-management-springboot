package com.library.controller;

import com.library.model.Author;
import com.library.model.Book;
import com.library.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/authors")
    public String viewAuthors(Model model) {
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "authors";
    }

    @GetMapping("/add-author")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "add-author";
    }

    @PostMapping("/add-author")
    public String addAuthor(@Valid @ModelAttribute("author") Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-author";
        }
        libraryService.saveAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/books")
    public String viewBooks(Model model) {
        model.addAttribute("books", libraryService.getAllBooks());
        return "books";
    }

    @GetMapping("/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", libraryService.getAllAuthors());
            return "add-book";
        }
        try {
            libraryService.saveBook(book);
            return "redirect:/books";
        } catch (IllegalArgumentException e) {
            model.addAttribute("authors", libraryService.getAllAuthors());
            model.addAttribute("errorMessage", e.getMessage());
            return "add-book";
        }
    }

    @GetMapping("/edit-author/{id}")
    public String showEditAuthorForm(@PathVariable("id") Long id, Model model) {
        Author author = libraryService.getAuthorById(id);
        if (author == null) return "redirect:/authors";
        model.addAttribute("author", author);
        return "add-author";
    }

    @GetMapping("/edit-book/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Book book = libraryService.getBookById(id);
        if (book == null) return "redirect:/books";
        model.addAttribute("book", book);
        model.addAttribute("authors", libraryService.getAllAuthors());
        return "add-book";
    }
}
