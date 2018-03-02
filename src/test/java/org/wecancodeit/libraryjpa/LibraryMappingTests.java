package org.wecancodeit.libraryjpa;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class LibraryMappingTests {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private BookRepository bookRepo;

	@Resource
	private AuthorRepository authorRepo;

	@Resource
	private GenreRepository genreRepo;

	@Test
	public void shouldSaveAndLoadBook() {
		Genre genre = new Genre("ska");
		genreRepo.save(genre);
		Author author = new Author("Kurt", "Vonnegut");
		authorRepo.save(author);
		Author author2 = new Author ("Chuck", "Pahlaniuk");
		authorRepo.save(author2);
		Book book = new Book("its name", genre, author, author2);
		book = bookRepo.save(book);
		long bookId = book.getId();
		entityManager.flush(); // forces pending stuff to happen
		entityManager.clear(); // detaches all entities, forces jpa to hit the db when we try to find an entity
		book = bookRepo.findOne(bookId);
		assertThat(book.getTitle(), is("its name"));
	}

	@Test //1 Genre to Many Books
	public void shouldSaveGenreToBookRelationshipOneGenreToManyBooks() {
		Genre genre = new Genre("Fiction");
		genreRepo.save(genre);
		long genreId = genre.getId();

		Book book = new Book("Another Name", genre);
		bookRepo.save(book);
		Book book2 = new Book("Second Book", genre);
		bookRepo.save(book2);
		entityManager.flush(); // forces pending stuff to happen
		entityManager.clear(); //

		genre = genreRepo.findOne(genreId);
		assertThat(genre.getBooks(), containsInAnyOrder(book, book2));
	}
	
	@Test
	public void shouldSaveAndLoadAuthor() {
		Author author = authorRepo.save(new Author("Mister", "Anderson"));
		long authorId = author.getId();
		
		entityManager.flush(); // forces pending stuff to happen
		entityManager.clear(); //
		
		author = authorRepo.findOne(authorId);
		assertThat(author.getFirstName(), is("Mister"));
		assertThat(author.getLastName(), is("Anderson"));
	
	}
	//book is not the owner. mapped by book.  test for book first
	@Test //many to many - many books to authors
	public void shouldEstablishAuthorToBookRelationship() {
		Genre genre = genreRepo.save(new Genre("scifi"));
		Book book1 = bookRepo.save(new Book("book1", genre));
		Book book2 = bookRepo.save(new Book("book2", genre));
		
		Author author = new Author("Frost", book1, book2);
		author = authorRepo.save(author);
		long frostId = author.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		author = authorRepo.findOne(frostId);
		assertThat(author.getBooks(), containsInAnyOrder(book1, book2));
		
	}
	//Author is the owner.
	@Test //many authors to books
	public void shouldEstablishBookToAuthorRelationship() {
		Genre genre = genreRepo.save(new Genre("nonfict"));
		Book book1 = bookRepo.save(new Book("Space", genre));
		
		long book1Id = book1.getId();
		
		Author author1 = new Author("Pahalniuk", book1);
		author1 = authorRepo.save(author1);
		
		Author author2 = new Author("Smith", book1);
		author2 = authorRepo.save(author2);
		
		entityManager.flush();
		entityManager.clear();
		
		book1 = bookRepo.findOne(book1Id);
		assertThat(book1.getAuthors(), containsInAnyOrder(author1, author2));
		
		
	}
	
}