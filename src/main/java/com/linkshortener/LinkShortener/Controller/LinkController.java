package com.linkshortener.LinkShortener.Controller;

import com.linkshortener.LinkShortener.DTO.ShortLinkRequest;
import com.linkshortener.LinkShortener.Service.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

@Controller
public class LinkController {

	private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

	private final LinkService linkService;

	public LinkController(LinkService linkService) {
		this.linkService = linkService;
	}

	@PostMapping(path = "link", consumes = {"application/x-www-form-urlencoded", "application/form-data"})
	@ResponseBody
	public String getShortLink(ShortLinkRequest request, HttpServletResponse res) {
		String shortLink = "";
		try {
			shortLink  = linkService.getShortLink(request.getFullLink(), res);
		} catch (NoSuchAlgorithmException e) {
			logger.error(String.valueOf(e));
		}
		return shortLink;
	}

	@GetMapping("/")
	public String getShortlinkForm(Model model) {
		model.addAttribute("shortLinkRequest", new ShortLinkRequest());
		return "index";
	}

	@GetMapping("link/{shortLink}")
	@ResponseBody
	public String getFullLink(@PathVariable("shortLink") String shortLink, HttpServletResponse res) throws IOException {
		String fullLink = "";
		try {
			fullLink = linkService.getFullLink(shortLink, res);
		} catch (NoSuchElementException e) {
			logger.error("No link found for short link: '{}'", shortLink);
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		res.sendRedirect(fullLink);
		return fullLink;
	}


}
