package com.digiwes.frameworx.engagedparty.party.bean;

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
}