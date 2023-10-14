package com.linkshortener.LinkShortener.Repository;

import com.linkshortener.LinkShortener.Model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Integer> {

	public Optional<Link> findByFullLink(String fullLink);

	public Optional<Link> findByShortenedLink(String shortenedLink);

}
