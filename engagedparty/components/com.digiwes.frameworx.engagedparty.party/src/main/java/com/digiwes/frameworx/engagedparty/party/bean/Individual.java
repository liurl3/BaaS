package com.digiwes.frameworx.engagedparty.party.bean;
import com.digiwes.frameworx.common.basetype.TimePeriod;
import java.util.*;

/**
 * Represents a single human being (a man, woman or child).
 * The individual could be a customer, an employee or any other person that the organization needs to store information about. An Individual is a type of Party.
 */
public class Individual  {

	private String individualId;
	
	private TimePeriod validFor;

	private Set<LanguageAbility> _languageAbilitys = new HashSet ();
	 
	private DefaultIndividualName _defaultIndividualName;
    
    private Set<OptionalIndividualName> _optionalIndividualName = new HashSet();
	/**
	 * A socially constructed role that implies behaviours, activities, and attributes.
	 */
	private String gender;
	/**
	 * Notes:  Used for legal requirements (special billing, special products, special contact media, such as Braille). This should be modeled as a separate entity but is shown as an attribute for brevity.
	 */
	private String disabilities;
	/**
	 * Note:
	 * Pointer to a Location object
	 * City, town name etc.
	 */
	private String placeOfBirth;
	/**
	 * Note:
	 * Pointer to a country object
	 */
	private String nationality;
	/**
	 * Permitted Values: married, never married, divorced, widowed
	 */
	private String maritalStatus;
	/**
	 * Note:
	 * Probably only used for employees, but is really role independent
	 * This should be modeled as a separate entity but is shown as an attribute for brevity
	 */
	private String skills;
	/**
	 * Birth date and death date.
	 */
	private TimePeriod aliveDuring;

	public String getIndividualId() {
		return individualId;
	}

	public void setIndividualId(String individualId) {
		this.individualId = individualId;
	}

	public TimePeriod getValidFor() {
		return validFor;
	}

	public void setValidFor(TimePeriod validFor) {
		this.validFor = validFor;
	}

	public Set<LanguageAbility> get_languageAbilitys() {
		return _languageAbilitys;
	}

	public void set_languageAbilitys(Set<LanguageAbility> _languageAbilitys) {
		this._languageAbilitys = _languageAbilitys;
	}

	public DefaultIndividualName get_defaultIndividualName() {
		return _defaultIndividualName;
	}

	public void set_defaultIndividualName(DefaultIndividualName _defaultIndividualName) {
		this._defaultIndividualName = _defaultIndividualName;
	}

	public Set<OptionalIndividualName> get_optionalIndividualName() {
		return _optionalIndividualName;
	}

	public void set_optionalIndividualName(Set<OptionalIndividualName> _optionalIndividualName) {
		this._optionalIndividualName = _optionalIndividualName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDisabilities() {
		return disabilities;
	}

	public void setDisabilities(String disabilities) {
		this.disabilities = disabilities;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public TimePeriod getAliveDuring() {
		return aliveDuring;
	}

	public void setAliveDuring(TimePeriod aliveDuring) {
		this.aliveDuring = aliveDuring;
	}
}
