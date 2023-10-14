package com.linkshortener.LinkShortener.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "links")
public class Link {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "shortened_link")
	private String shortenedLink;

	@Column(name = "full_link")
	private String fullLink;

	public Link() {
		super();
	}

	public Link(String shortenedLink, String fullLink) {
		super();
		this.shortenedLink = shortenedLink;
		this.fullLink = fullLink;
	}

	public Link(int id, String shortenedLink, String fullLink) {
		super();
		this.id = id;
		this.shortenedLink = shortenedLink;
		this.fullLink = fullLink;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShortenedLink() {
		return shortenedLink;
	}

	public void setShortenedLink(String shortenedLink) {
		this.shortenedLink = shortenedLink;
	}

	public String getFullLink() {
		return fullLink;
	}

	public void setFullLink(String fullLink) {
		this.fullLink = fullLink;
	}

	@Override
	public String toString() {
		return "Link{" +
				"id=" + id +
				", shortenedLink='" + shortenedLink + '\'' +
				", fullLink='" + fullLink + '\'' +
				'}';
	}
}
