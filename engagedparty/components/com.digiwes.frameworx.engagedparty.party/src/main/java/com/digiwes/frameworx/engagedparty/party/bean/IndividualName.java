package com.digiwes.frameworx.engagedparty.party.bean;

import com.digiwes.frameworx.common.basetype.TimePeriod;

/**
 * A word, term, or phrase by which an individual is known and distinguished from other individuals.A name is an informal way of identifying an object [Fowler]This entity allows for international naming variations. An IndividualName is a type of PartyName.
 */
public class IndividualName   {

	private String partyNameId;

	private TimePeriod validFor;
	/**
	 * Contains, in one string, a fully formatted name with all of its pieces in their proper place. This includes all of the necessary punctuation. This de-normalized form of the name cannot be easily parsed.
	 * 
	 * Note:
	 * if NULL, then derive from the other fields using name policy
	 */
	private String formattedName;
	/**
	 * Contains, in one string, a fully formatted name with all of its pieces in their proper place. This includes all of the necessary punctuation
	 * 
	 * Note:
	 * if NULL, then derive from the other fields using name policy
	 */
	private String legalName;
	/**
	 * A name that describes someone's aristocratic position, such as Baron, Graf, Earl, and so forth.
	 */
	private String aristocraticTitle;
	/**
	 * Contains the Salutation,e.g. Mr., Mrs., Hon., Dr.,Major, etc
	 * 
	 * Note:
	 * Also known as person title
	 * Also includes. Miss, Ms,
	 */
	private String formOfAddress;
	/**
	 * An abbreviation or word that pertains to the generation in a family history, such as Sr., Jr., III (the third), and so forth.
	 */
	private String generation;
	/**
	 * Note:
	 * Also known as Christian name, chosen name, first name 
	 * Multiple fields may be entered with a delimiter in-between or stored in a collection
	 */
	
	private String givenNames;
	/**
	 * Contains the chosen name by which the person prefers to be addressed. Note: This name may be a name other than a given name, such as a nickname
	 */
	private String preferredGivenName;
	/**
	 * Middle name(s) or initial(s)
	 * Note:
	 * Multiple fields may be entered with a delimiter in-between or stored in a collection
	 */
	private String middleNames;
	/**
	 * Notes:
	 * e.g. Van den, Von etc.
	 */
	private String familyNamePrefix;
	/**
	 * Contains the non-chosen or inherited name. Also known as a person's last name in the Western context.
	 * 
	 * Notes:
	 * Also known as surname 
	 * Multiple fields may be entered with a delimiter in-between
	 */
	private String familyNames;
	/**
	 * An abbreviation or word that pertains to the generation in a family, such Sr, Jr.
	 */
	private String familyGeneration;
	/**
	 * Contains the letters used to describe academic or other type qualifications held by a person and/or the distinctions conferred upon them. e.g. PhD, MD, CPA, MCSD, etc
	 * 
	 * Note:
	 * also known as orders, decorations, honors, awards and distinctions
	 */
	private String qualifications;

	public String getPartyNameId() {
		return partyNameId;
	}

	public void setPartyNameId(String partyNameId) {
		this.partyNameId = partyNameId;
	}

	public TimePeriod getValidFor() {
		return validFor;
	}

	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}

	public String getFormattedName() {
		return formattedName;
	}

	public void setFormattedName(String formattedName) {
		this.formattedName = formattedName;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getAristocraticTitle() {
		return aristocraticTitle;
	}

	public void setAristocraticTitle(String aristocraticTitle) {
		this.aristocraticTitle = aristocraticTitle;
	}

	public String getFormOfAddress() {
		return formOfAddress;
	}

	public void setFormOfAddress(String formOfAddress) {
		this.formOfAddress = formOfAddress;
	}

	public String getGeneration() {
		return generation;
	}

	public void setGeneration(String generation) {
		this.generation = generation;
	}

	public String getGivenNames() {
		return givenNames;
	}

	public void setGivenNames(String givenNames) {
		this.givenNames = givenNames;
	}

	public String getPreferredGivenName() {
		return preferredGivenName;
	}

	public void setPreferredGivenName(String preferredGivenName) {
		this.preferredGivenName = preferredGivenName;
	}

	public String getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}

	public String getFamilyNamePrefix() {
		return familyNamePrefix;
	}

	public void setFamilyNamePrefix(String familyNamePrefix) {
		this.familyNamePrefix = familyNamePrefix;
	}

	public String getFamilyNames() {
		return familyNames;
	}

	public void setFamilyNames(String familyNames) {
		this.familyNames = familyNames;
	}

	public String getFamilyGeneration() {
		return familyGeneration;
	}

	public void setFamilyGeneration(String familyGeneration) {
		this.familyGeneration = familyGeneration;
	}

	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}
}