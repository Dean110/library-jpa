package org.wecancodeit.libraryjpa;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LibraryController {
	
	@Resource
	private BookRepository bookRepo;

	@Resource
	private AuthorRepository authorRepo;

	@Resource
	private GenreRepository genreRepo;

	@RequestMapping(value = "books")
	public String getAllBooks(Model model) {
	model.addAttribute("books", bookRepo.findAll());
	return "books";
	}
	
	
}
