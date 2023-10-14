package com.linkshortener.LinkShortener.Service;

import com.linkshortener.LinkShortener.Model.Link;
import com.linkshortener.LinkShortener.Repository.LinkRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LinkService {

	private static final Logger logger = LoggerFactory.getLogger(LinkService.class);

	private final LinkRepository repo;

	public LinkService(LinkRepository repo) {
		this.repo = repo;
	}

	public String getShortLink(String fullLink, HttpServletResponse res) throws NoSuchAlgorithmException {

		UrlValidator validator = new UrlValidator();
		if (!validator.isValid(fullLink)) {
			logger.error("Short link requested for invalid link: '{}'", fullLink);
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return "";
		}
		logger.debug("Retrieving shortened link for link: {}", fullLink);
		Optional<Link> linkOptional = repo.findByFullLink(fullLink);

		// If full link has already been shortened before, return existing shortened link
		if (linkOptional.isPresent()) {
			return linkOptional.get().getShortenedLink();
		}
		// Else shorten link and return value
		Link newLink = generateShortLink(fullLink);
		return newLink.getShortenedLink();
	}

	private Link generateShortLink(String fullLink) throws NoSuchAlgorithmException {
		logger.debug("Generating short link for link: '{}'", fullLink);

		String hash = DigestUtils.md5Hex(fullLink);
		String shortLink = hash.toLowerCase().substring(0, 8);

		Link link = new Link(shortLink, fullLink);
		link = repo.save(link);
		logger.debug("Generated new short link: {}", link);

		return link;

	}

	public String getFullLink(String shortLink, HttpServletResponse res) throws NoSuchElementException {
		if (shortLink.length() != 8) {
			logger.error("Full link requested for invalid short link: '{}'", shortLink);
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		logger.debug("Retrieving full link for short link: '{}'", shortLink);
		Optional<Link> linkOptional = repo.findByShortenedLink(shortLink);
		Link link = linkOptional.orElseThrow();

		return link.getFullLink();
	}

}
