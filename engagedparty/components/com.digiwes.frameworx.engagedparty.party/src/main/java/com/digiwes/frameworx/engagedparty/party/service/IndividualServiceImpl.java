package com.digiwes.frameworx.engagedparty.party.service;

import com.digiwes.frameworx.common.basetype.TimePeriod;
import com.digiwes.frameworx.engagedparty.party.bean.*;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualQueryService;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualUpdateService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
    public Individual modifyLanguageAbility(Individual individual, LanguageAbility fromLanguageAbility, LanguageAbility toLanguageAbility) throws Exception{

        if(!individual.get_languageAbilitys().contains(fromLanguageAbility)){
            throw new Exception(" has no ability");
        }
        for(LanguageAbility languageAbility : individual.get_languageAbilitys()){
            if(languageAbility.equals(fromLanguageAbility)){
                languageAbility.setListeningProficiency(toLanguageAbility.getListeningProficiency());
                languageAbility.setReadingProficiency(toLanguageAbility.getReadingProficiency());
                languageAbility.setSpeakingProficiency(toLanguageAbility.getSpeakingProficiency());
                languageAbility.setWritingProficiency(toLanguageAbility.getWritingProficiency());
            }
            break;
        }
        return individual;
    }

    @Override
    public Individual modifyDefaultName(Individual individual, IndividualName name) {

        if(name .equals(individual.get_defaultIndividualName())){
            return  individual;
        }
        Date defaultPartyEndTime = new Date();
        // edit oldDefault EndDateTime
        DefaultIndividualName oldDefaultInvidualName = individual.get_defaultIndividualName();
        if(oldDefaultInvidualName !=null) {
            OptionalIndividualName historyName = convertOptionalIndividualName(individual, oldDefaultInvidualName);
            if (null == historyName.getValidFor()) {
                TimePeriod validFor = new TimePeriod(null, new Date(defaultPartyEndTime.getTime() - 2*1000));
                historyName.setValidFor(validFor);
            } else {
                historyName.getValidFor().setEndDateTime(new Date(defaultPartyEndTime.getTime() - 2*1000));
            }
            DefaultIndividualName defaultIndividualName =  covertToDefautlName(individual, name);
            if( null == defaultIndividualName.getValidFor()){
                TimePeriod validFor = new TimePeriod(defaultPartyEndTime,null);
                defaultIndividualName.setValidFor(validFor);
            } else {
                defaultIndividualName.getValidFor().setEndDateTime(defaultPartyEndTime);
            }
            defaultIndividualName.setPartyNameId(oldDefaultInvidualName.getPartyNameId());
            if(individual.get_defaultIndividualName()!=null){
                individual.get_defaultIndividualName().copyFrom(defaultIndividualName);
            }
            historyName.setPartyNameId(null);
            individual.get_optionalIndividualName().add(historyName);
        }
        return  individual;
    }

    @Override
    public Individual addOptionalIndividualName(Individual individual, IndividualName name) {
        OptionalIndividualName aliasIndividualName = convertOptionalIndividualName(individual, name);
        Set<OptionalIndividualName> optionalIndividualNameSet = new HashSet();
        optionalIndividualNameSet.add(aliasIndividualName);
        individual.set_optionalIndividualName(optionalIndividualNameSet);
        return individual;
    }

    @Override
    public Individual modifyAlias(Individual individual, IndividualName fromName, IndividualName toName) {
        OptionalIndividualName oldaliasIndividualName = convertOptionalIndividualName(individual, fromName);
        for (OptionalIndividualName optionalIndividualName : individual.get_optionalIndividualName() ){
            if(oldaliasIndividualName.equals(optionalIndividualName)){
                individual.get_optionalIndividualName().remove(optionalIndividualName);
                OptionalIndividualName aliasIndividualName = convertOptionalIndividualName(individual, toName);
                individual.get_optionalIndividualName().add(aliasIndividualName);
                oldaliasIndividualName.set_individual(null);
                return individual;
            }
        }
        return null;
    }

    @Override
    public boolean eliminateAlias(Individual individual, IndividualName name) {
        OptionalIndividualName oldaliasIndividualName = convertOptionalIndividualName(individual, name);
        for (OptionalIndividualName optionalIndividualName : individual.get_optionalIndividualName() ){
            if(optionalIndividualName .equals(oldaliasIndividualName)){
                individual.get_optionalIndividualName().remove(optionalIndividualName);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean delIndividual(String partyId) {

        return false;
    }

    @Override
    public Individual createIndividual(Individual individual) {

        return null;
    }

    private OptionalIndividualName convertOptionalIndividualName (Individual individual, IndividualName invidualName){
        OptionalIndividualName optionalIndividualName = new OptionalIndividualName();
        optionalIndividualName.copyFrom(invidualName);
        optionalIndividualName.set_individual(individual);
        return  optionalIndividualName;
    }

    private DefaultIndividualName covertToDefautlName(Individual individual, IndividualName invidualName){
        DefaultIndividualName defaultIndividualName = new DefaultIndividualName();
        defaultIndividualName.copyFrom(invidualName);
        defaultIndividualName.set_individual(individual);
        return defaultIndividualName;
    }
}
