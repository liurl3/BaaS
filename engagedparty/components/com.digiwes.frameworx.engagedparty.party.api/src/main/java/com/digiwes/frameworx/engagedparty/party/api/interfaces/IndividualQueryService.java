package com.digiwes.frameworx.engagedparty.party.api.interfaces;

import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.bean.IndividualName;
import com.digiwes.frameworx.engagedparty.party.bean.LanguageAbility;

import java.util.Date;
import java.util.List;

public interface IndividualQueryService{
	
	
	public LanguageAbility retrieveLanguageAbility( Individual individual,String alphabetName,  String dailectName) ;
	
	public List<IndividualName> retrieveOptionalIndividualName(Individual individual,Date date)  ;
	
	public Individual retrieveIndividualById(String partyId);
	

	
}