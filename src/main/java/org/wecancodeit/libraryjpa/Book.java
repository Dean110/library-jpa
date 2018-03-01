package org.wecancodeit.libraryjpa;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Book {
	@Id
	@GeneratedValue
	private long id;
	private String title;

	@OneToMany(mappedBy = "book")
	private Collection<Genre> genres;

	@ManyToMany(mappedBy = "books")
	private Collection<Author> authors;

	public Collection<Genre> getGenres() {
		return genres;
	}

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

	public Book(String title, Genre... genres) {
		this.title = title;
		this.genres = new HashSet<>(asList(genres));
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
