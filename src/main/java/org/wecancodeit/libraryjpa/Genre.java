package org.wecancodeit.libraryjpa;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Genre {

	@Id
	@GeneratedValue
	private long id;

	@OneToMany(mappedBy = "genre")
	private Collection<Book> books;

	private String type;

	public long getId() {
		return id;
	}

	public Collection<Book> getBooks() {

		return books;
	}

	public Genre(String type) {
		this.type = type;

	}

	public String getType() {
		return type;
	}

	@SuppressWarnings("unused")
	private Genre() {
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

		return id == ((Genre) obj).id;
	}

}