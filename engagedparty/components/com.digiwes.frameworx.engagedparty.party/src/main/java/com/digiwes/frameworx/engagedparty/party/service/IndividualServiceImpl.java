package com.digiwes.frameworx.engagedparty.party.service;

import com.digiwes.frameworx.common.basetype.TimePeriod;
import com.digiwes.frameworx.engagedparty.party.api.interfaces.IndividualQueryService;
import com.digiwes.frameworx.engagedparty.party.api.interfaces.IndividualUpdateService;
import com.digiwes.frameworx.engagedparty.party.bean.*;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;

import java.util.*;

@Component(name = "individualService",immediate = true)
@Service(value = {IndividualQueryService.class,IndividualUpdateService.class})
public class IndividualServiceImpl implements IndividualQueryService ,IndividualUpdateService{

    public static int individualId = 1;
    public static Map<String, Individual> individualMap = new HashMap();
    private static boolean isInit = false;
    @Activate
    protected void activate(ComponentContext ctxt) {
        if (!isInit) {
            individualMap = new HashMap();
            System.out.println("IndividualServiceImpl DataInit -------------------------------");
            try {
                //main(null);
                initData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isInit = true;
        }
    }

    protected void deactivate(ComponentContext ctxt){
        System.out.print("IndividualServiceImpl deactivate ###############################################");
    }

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
        for(String key : individualMap.keySet()){
            if(key.equals(partyId)){
                return individualMap.get(key);
            }
        }
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
            DefaultIndividualName defaultIndividualName =  covertToDefaultName(individual, name);
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
    public Individual addOptionalIndividualName(Individual individual, IndividualName name)throws Exception{
        if(null==individual){
            throw new Exception("individual must not be null");
        }
        if(null==name){
            throw new Exception("individualName must not be null");
        }
        OptionalIndividualName aliasIndividualName = convertOptionalIndividualName(individual, name);
        individual.get_optionalIndividualName().add(aliasIndividualName);
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
            if(optionalIndividualName.equals(oldaliasIndividualName)){
                individual.get_optionalIndividualName().remove(optionalIndividualName);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean delIndividual(String partyId) {
        for(String key : individualMap.keySet()){
            if(key.equals(partyId)){
                individualMap.remove(key);
            }
        }
        return true;
    }

    @Override
    public Individual createIndividual(Individual individual) {
        individual.setIndividualId(individualId+"");
        individualMap.put(individualId + "", individual);
        individualId++;
        return individual;
    }

    private OptionalIndividualName convertOptionalIndividualName (Individual individual, IndividualName invidualName){
        if(invidualName !=null ){
            OptionalIndividualName optionalIndividualName = new OptionalIndividualName(invidualName.getGivenNames(), invidualName.getFamilyNames());
            //optionalIndividualName.copyFrom(invidualName);
            optionalIndividualName.set_individual(individual);
            return  optionalIndividualName;
        }
        return null;
    }

    private DefaultIndividualName covertToDefaultName(Individual individual, IndividualName invidualName){
        DefaultIndividualName defaultIndividualName = new DefaultIndividualName("givenName","familyName");
        defaultIndividualName.copyFrom(invidualName);
        defaultIndividualName.set_individual(individual);
        return defaultIndividualName;
    }

    public   void initData()throws Exception{
        String[][] defaultIndividualNameStrs = {
                {"YunPing","Zhao"},{"WenHua","Dong"}
        };
        String [][] optionalIndividualNameStrs={ {"PingPing","Zhao"},{"XiaoYu","Dong"}};
        String[][] languageAbilityStr = {
                {"Chinese","Chinese","mastery","mastery","mastery","good"},{"English","English","mastery","mastery","mastery","good"}
        } ;

        Individual individual = null;
        Date current = new Date();
        Date endDate = new Date();
        endDate.setMonth(9);
        TimePeriod timePeriod = new TimePeriod(current, endDate);
         for (int i =0  ; i<defaultIndividualNameStrs.length ; i++){
            DefaultIndividualName defaultIndividualName = new DefaultIndividualName(defaultIndividualNameStrs[i][0],defaultIndividualNameStrs[i][1]);
            OptionalIndividualName optionalIndividualName = new OptionalIndividualName(optionalIndividualNameStrs[i][0],optionalIndividualNameStrs[i][1]);
            individual = new Individual(defaultIndividualName, timePeriod, "Beijing");
            individual.setIndividualId(individualId+"");
             individual.setGender("Female");
            Language language = new Language(languageAbilityStr[i][0],languageAbilityStr[i][1]);
            LanguageAbility languageAbility = new LanguageAbility(language,languageAbilityStr[i][2],languageAbilityStr[i][3],languageAbilityStr[i][4],languageAbilityStr[i][5]);
            this.hasLanguageAbility(individual, languageAbility);
            this.addOptionalIndividualName(individual, optionalIndividualName);
            individualMap.put(individualId+"",individual);
             individualId++;
            if (i == 1) {
                optionalIndividualName = new OptionalIndividualName("dagexing", "Dong");
                addOptionalIndividualName(individual, optionalIndividualName);
            }
         }
        System.out.println("IndividualServiceImpl initDate ======================================= "+individualMap.size());
    }
     public static void main(String[] args) throws Exception{
        IndividualServiceImpl individualService = new IndividualServiceImpl();
        individualService.initData();

    }
}
