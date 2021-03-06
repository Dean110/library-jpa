package org.wecancodeit.libraryjpa;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import static java.util.Arrays.asList;


@Entity
public class Book {
	@Id
	@GeneratedValue
	private long id;
	private String title;

	@ManyToOne
	private Genre genre;

	@ManyToMany(mappedBy = "books")
	private Collection<Author> authors;


	public Collection<Author> getAuthors() {
		return authors;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	@SuppressWarnings("unused")
	private Book() {
	}

	public Book(String title, Genre genre, Author...authors) {
		this.title = title;
		this.genre = genre;
		this.authors = new HashSet<>(asList(authors));

	}

	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return id == ((Book) obj).id;
	}

}