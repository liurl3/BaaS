package com.digiwes.frameworx.engagedparty.party.api.bean;
  
/**
 * Represents a spoken and/or written language.
 */
public class Language {

	private String languageId;

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	/**
	 * The alphabet name use for the language
	 * 
	 * Note:
	 * ISO standard ?
	 */
	
	private String alphabetName;
	/**
	 * A list of the dialects of the language
	 * 
	 * Note:
	 * ISO standard ?
	 */
	
	private String dialectNames;

	
	public String getAlphabetName() {
		return this.alphabetName;
	}

	public void setAlphabetName( String alphabetName) {
		this.alphabetName = alphabetName;
	}

	
	public String getDialectNames() {
		return this.dialectNames;
	}

	public void setDialectNames( String dialectNames) {
		this.dialectNames = dialectNames;
	}

	public  Language ( String alphabetName, String dialectNames ){
		this.alphabetName = alphabetName ;
		this.dialectNames = dialectNames ;
	}
	public Language(){

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || this.getClass() != o.getClass()) return false;
		Language language = (Language) o;
		if (!this.alphabetName.equals(language.alphabetName)) return false;
		return dialectNames.equals(language.dialectNames);

	}

	@Override
	public int hashCode() {
		int result = this.alphabetName.hashCode();
		result = 31 * result + this.dialectNames.hashCode();
		return result;
	}
}