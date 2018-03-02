package org.wecancodeit.libraryjpa;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BooksPopulator implements CommandLineRunner {
	@Resource
	private BookRepository bookRepo;

	@Resource
	private AuthorRepository authorRepo;

	@Resource
	private GenreRepository genreRepo;
	
	
	@Override
	public void run(String... args) throws Exception {
		Author kathySierra = new Author ("Kathy", "Sierra");
		kathySierra = authorRepo.save(kathySierra);		
		Author bertBates = new Author("Bert", "Bates");
		bertBates = authorRepo.save(bertBates);
		Author elisabethFreeman = new Author ("Elisabeth", "Freeman");
		elisabethFreeman = authorRepo.save(elisabethFreeman);
		Author cliffordStoll = new Author ("Clifford", "Stoll");
		cliffordStoll = authorRepo.save(cliffordStoll);

		
		//one side of relationship one genre
		Genre fiction = genreRepo.save(new Genre ("Fiction"));
		Genre nonfiction = genreRepo.save(new Genre("Non-Fiction"));
		
		
		//many side of relationship many books
		bookRepo.save(new Book("Head First Design Patterns", nonfiction, kathySierra, bertBates));
		bookRepo.save(new Book("Head First Java", nonfiction, kathySierra, elisabethFreeman));
		bookRepo.save(new Book("The Cukoo's Egg", fiction, cliffordStoll));
//		genreRepo.save(new Genre("ska"));
		
		
		
	}

}