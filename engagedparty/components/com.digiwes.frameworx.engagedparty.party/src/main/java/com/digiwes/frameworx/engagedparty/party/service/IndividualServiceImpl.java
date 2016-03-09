package com.digiwes.frameworx.engagedparty.party.service;

import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.bean.IndividualName;
import com.digiwes.frameworx.engagedparty.party.bean.LanguageAbility;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualQueryService;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualUpdateService;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaoyp-pc on 2016/3/9.
 */
public class IndividualServiceImpl implements IndividualQueryService ,IndividualUpdateService{
    @Override
    public LanguageAbility retrieveLanguageAbility(Individual individual, String alphabetName, String dailectName) {
        return null;
    }

    @Override
    public List<IndividualName> retrieveOptionalIndividualName(Individual individual, Date date) {
        return null;
    }

    @Override
    public Individual retrieveIndividualById(String partyId) {
        return null;
    }

    @Override
    public Individual hasLanguageAbility(Individual individual, LanguageAbility languageAbility) {
        return null;
    }

    @Override
    public Individual modifyLanguageAbility(Individual individual, LanguageAbility fromLanguageAbility, LanguageAbility toLanguageAbility) {
        return null;
    }

    @Override
    public Individual modifyDefaultName(Individual individual, IndividualName name) {
        return null;
    }

    @Override
    public Individual addOptionalIndividualName(Individual individual, IndividualName name) {
        return null;
    }

    @Override
    public Individual modifyAlias(Individual individual, IndividualName fromName, IndividualName toName) {
        return null;
    }

    @Override
    public boolean eliminateAlias(Individual individual, IndividualName name) {
        return false;
    }

    @Override
    public boolean delIndividual(String partyId) {
        return false;
    }

    @Override
    public Individual createIndividual(Individual individual) {
        return null;
    }
}
