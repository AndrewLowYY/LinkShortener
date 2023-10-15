package com.linkshortener.LinkShortener.DTO;

public class ShortLinkRequest {
	private String fullLink;

	public ShortLinkRequest() {
		super();
	}

	public ShortLinkRequest(String fullLink) {
		this.fullLink = fullLink;
	}

	public String getFullLink() {
		return fullLink;
	}

	public void setFullLink(String fullLink) {
		this.fullLink = fullLink;
	}
}