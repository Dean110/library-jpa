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
		Book book = new Book("its name", null);
		book = bookRepo.save(book);
		long bookId = book.getId();
		entityManager.flush(); // forces pending stuff to happen
		entityManager.clear(); // detaches all entities, forces jpa to hit the db when we try to find an entity
		book = bookRepo.findOne(bookId);
		assertThat(book.getTitle(), is("its name"));
	}

	@Test
	public void shouldSaveGenreToBookRelationship() {
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
}
