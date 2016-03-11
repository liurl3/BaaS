package com.digiwes.frameworx.engagedparty.party.api.bean;

import javax.validation.constraints.NotNull;

/**
 * Represents the ability of a Party to understand or converse in a Language
 */
public class LanguageAbility {

	private String languageAbilityId;
	private Individual _individual ;
	@NotNull
	private Language _language;
	/**
	 * Degree of mastery in writing a language.
	 */
	private String writingProficiency;
	/**
	 * Degree of mastery in reading a language.
	 */
	private String readingProficiency;
	/**
	 * Degree of mastery in speaking a language.
	 */
	private String speakingProficiency;

	/**
	 * Degree of mastery in listening to a language.
	 */
	private String listeningProficiency;

	public String getLanguageAbilityId() {
		return languageAbilityId;
	}

	public void setLanguageAbilityId(String languageAbilityId) {
		this.languageAbilityId = languageAbilityId;
	}

	public Individual get_individual() {
		return _individual;
	}

	public void set_individual(Individual _individual) {
		this._individual = _individual;
	}

	public Language get_language() {
		return _language;
	}

	public void set_language(Language _language) {
		this._language = _language;
	}

	public String getWritingProficiency() {
		return writingProficiency;
	}

	public void setWritingProficiency(String writingProficiency) {
		this.writingProficiency = writingProficiency;
	}

	public String getReadingProficiency() {
		return readingProficiency;
	}

	public void setReadingProficiency(String readingProficiency) {
		this.readingProficiency = readingProficiency;
	}

	public String getSpeakingProficiency() {
		return speakingProficiency;
	}

	public void setSpeakingProficiency(String speakingProficiency) {
		this.speakingProficiency = speakingProficiency;
	}

	public String getListeningProficiency() {
		return listeningProficiency;
	}

	public void setListeningProficiency(String listeningProficiency) {
		this.listeningProficiency = listeningProficiency;
	}
}