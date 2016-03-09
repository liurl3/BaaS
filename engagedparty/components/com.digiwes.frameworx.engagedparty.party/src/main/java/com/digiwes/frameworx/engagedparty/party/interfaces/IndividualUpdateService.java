package com.digiwes.frameworx.engagedparty.party.interfaces;

import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.bean.IndividualName;
import com.digiwes.frameworx.engagedparty.party.bean.LanguageAbility;

public interface IndividualUpdateService{
	
	public Individual hasLanguageAbility(Individual individual, LanguageAbility languageAbility) ;
	
	public Individual modifyLanguageAbility(Individual individual, LanguageAbility fromLanguageAbility, LanguageAbility toLanguageAbility);
	
	public Individual modifyDefaultName(Individual individual,  IndividualName name);
	 
	public Individual addOptionalIndividualName(Individual individual, IndividualName name) ;
	
	public Individual modifyAlias(Individual individual,  IndividualName fromName,   IndividualName toName) ;
	
	public boolean eliminateAlias( Individual individual,  IndividualName name);
	
	public boolean delIndividual(String partyId);

	public Individual createIndividual(Individual individual);
}