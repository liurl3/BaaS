package com.digiwes.frameworx.engagedparty.party.bean;
public class OptionalIndividualName extends IndividualName {

	private Individual _individual;

	public Individual get_individual() {
		return _individual;
	}

	public void set_individual(Individual _individual) {

		this._individual = _individual;
	}

	public OptionalIndividualName() {

	}

	public OptionalIndividualName(String familyName, String givenName) {
		super(familyName, givenName);
	}


}