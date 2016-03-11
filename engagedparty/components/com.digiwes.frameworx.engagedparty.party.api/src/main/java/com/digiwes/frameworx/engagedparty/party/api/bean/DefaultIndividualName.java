package com.digiwes.frameworx.engagedparty.party.api.bean;

import java.io.Serializable;

public class DefaultIndividualName extends IndividualName implements Serializable{

	private Individual _individual;

	public Individual get_individual() {

		return this._individual;
	}

	public void set_individual(Individual _individual) {

		this._individual = _individual;
	}

	public DefaultIndividualName() {
	}

	public DefaultIndividualName( String givenName, String familyName) {

		super(givenName,familyName);
	}
}