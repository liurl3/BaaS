package com.digiwes.frameworx.engagedparty.party.service;

import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.bean.IndividualName;
import com.digiwes.frameworx.engagedparty.party.bean.LanguageAbility;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualQueryService;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualUpdateService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component(name = "individualService",immediate = true)
@Service(value = {IndividualQueryService.class,IndividualUpdateService.class})
public class IndividualServiceImpl implements IndividualQueryService ,IndividualUpdateService{
    @Override
    public LanguageAbility retrieveLanguageAbility(Individual individual, String alphabetName, String dailectName) {
        if( null != individual ){
            Set<LanguageAbility> _languageAbilitys = individual.get_languageAbilitys();
            if(_languageAbilitys!= null){
                for(LanguageAbility languageAbility :_languageAbilitys){
                    if(languageAbility.get_language().getAlphabetName().equals(alphabetName) && languageAbility.get_language().getDialectNames().equals(dailectName)){
                        return  languageAbility ;
                    }
                }
            }
        }else{
            throw  new IllegalArgumentException("invididual  must not be  null ");
        }
        return null;
    }

    @Override
    public List<IndividualName> retrieveOptionalIndividualName(Individual individual, Date date) {
        List<IndividualName> partyNames = new ArrayList() ;
        if( null != individual ){
            if(null != individual.get_optionalIndividualName() ){
                for (IndividualName individualName :individual.get_optionalIndividualName()){
                    if(date == null || null == individualName.getValidFor()  || individualName.getValidFor().isCover(date)){
                        partyNames.add(individualName);
                    }
                }
            }
        }else{
            throw  new IllegalArgumentException("invididual  must not be  null ");
        }
        return partyNames ;
    }

    @Override
    public Individual retrieveIndividualById(String partyId) {

        return null;
    }

    @Override
    public Individual hasLanguageAbility(Individual individual, LanguageAbility languageAbility) {
        if(null != individual ){
            individual.get_languageAbilitys().add(languageAbility);
            return  individual;
        }else{
            throw  new IllegalArgumentException("invididual  must not be  null ");
        }

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
