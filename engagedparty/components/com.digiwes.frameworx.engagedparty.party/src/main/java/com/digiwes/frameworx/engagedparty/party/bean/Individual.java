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

}
