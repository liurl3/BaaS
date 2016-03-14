package com.digiwes.frameworx.engagedparty.party.service;

import com.digiwes.frameworx.common.basetype.TimePeriod;
import com.digiwes.frameworx.engagedparty.party.api.bean.*;
import com.digiwes.frameworx.engagedparty.party.api.interfaces.IndividualQueryService;
import com.digiwes.frameworx.engagedparty.party.api.interfaces.IndividualUpdateService;

import org.apache.commons.lang3.time.DateUtils;
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
            System.out.println("DataInit");
            try {
                //main(null);
                initData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isInit = true;
        }
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
        OptionalIndividualName optionalIndividualName = new OptionalIndividualName("familyNameOption", "givenNameOption");
        optionalIndividualName.copyFrom(invidualName);
        optionalIndividualName.set_individual(individual);
        return  optionalIndividualName;
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
        TimePeriod validFor = new TimePeriod(DateUtils.parseDate("2016-03-01", "yyyy-MM-dd"),DateUtils.parseDate("2016-05-01","yyyy-MM-dd"));
        for (int i =0  ; i<defaultIndividualNameStrs.length ; i++){
            DefaultIndividualName defaultIndividualName = new DefaultIndividualName(defaultIndividualNameStrs[i][0],defaultIndividualNameStrs[i][1]);
            OptionalIndividualName optionalIndividualName = new OptionalIndividualName(optionalIndividualNameStrs[i][0],optionalIndividualNameStrs[i][1]);
            individual = new Individual(defaultIndividualName, validFor, "Beijing");
            Language language = new Language(languageAbilityStr[i][0],languageAbilityStr[i][1]);
            LanguageAbility languageAbility = new LanguageAbility(language,languageAbilityStr[i][2],languageAbilityStr[i][3],languageAbilityStr[i][4],languageAbilityStr[i][5]);
            this.hasLanguageAbility(individual,languageAbility);
            this.addOptionalIndividualName(individual,optionalIndividualName);
            individualMap.put((individualId++)+"",individual);
        }
        System.out.println("IndividualServiceImpl initDate ======================================= "+individualMap.size());
    }

   /* public static void main(String[] args) throws Exception{
        IndividualServiceImpl individualService = new IndividualServiceImpl();
        *//*data init*//*
        DefaultIndividualName defaultIndividualNameOne = new DefaultIndividualName("givenNameOne","familyNameOne");
        DefaultIndividualName defaultIndividualNameTwo = new DefaultIndividualName("givenNameTwo","familyNameTwo");
        DefaultIndividualName defaultIndividualNameThree = new DefaultIndividualName("givenNameThree","familyNameThree");
        DefaultIndividualName defaultIndividualNameFour = new DefaultIndividualName("givenNameFour","familyNameFour");
        Individual individualOne = new Individual(defaultIndividualNameOne, TimePeriod.DEFAULT_VALID_FOR, "birthPlace");
        Individual individualTwo = new Individual(defaultIndividualNameTwo, TimePeriod.DEFAULT_VALID_FOR, "birthPlace");
        Individual individualThree = new Individual(defaultIndividualNameThree, TimePeriod.DEFAULT_VALID_FOR, "birthPlace");
        Individual individualFour = new Individual(defaultIndividualNameFour, TimePeriod.DEFAULT_VALID_FOR, "birthPlace");

        *//*create individual*//*
        Individual individualGetOne = individualService.createIndividual(individualOne);
        System.out.println(individualGetOne.getIndividualId());

        Individual individualGetTwo = individualService.createIndividual(individualTwo);
        System.out.println(individualGetTwo.getIndividualId());

        Individual individualGetThree = individualService.createIndividual(individualThree);
        System.out.println(individualGetThree.getIndividualId());

        Individual individualGetFour = individualService.createIndividual(individualFour);
        System.out.println(individualGetFour.getIndividualId());

        Individual individual = individualService.retrieveIndividualById("2");
        System.out.println("individual information = " + individual.getIndividualId());

        System.out.println("map size ===" + individualMap.size());
        boolean rtnFlag = individualService.delIndividual("4");
        System.out.println("rtnFlag===" + rtnFlag);
        System.out.println("map size ===" + individualMap.size());


        *//*add optionalName*//*
        IndividualName individualNameOne = new IndividualName("familyNameIndividual", "givenNameIndividual");
        Individual individualOptionNameOne = individualService.addOptionalIndividualName(individualGetOne, individualNameOne);
        Set<OptionalIndividualName> optionNameOneSet = individualOptionNameOne.get_optionalIndividualName();
        for(OptionalIndividualName optionalIndividualName : optionNameOneSet){
            String familyName = optionalIndividualName.getFamilyNames();
            System.out.println("familyName==="+familyName);
        }
        System.out.println("individualOptionNameOne==="+individualOptionNameOne.get_optionalIndividualName().size());

        *//*retrieve optional name*//*
        Date date = new Date();
        List<IndividualName> individualNameList = individualService.retrieveOptionalIndividualName(individualGetOne, date);
        System.out.println("individualNameList======================"+individualNameList.size());

        *//*modify optionalName*//*
        IndividualName individualNameOneTo = new IndividualName("familyNameIndividualTo", "givenNameIndividualTo");
        Individual individualOptionNameOneTo = individualService.modifyAlias(individualGetOne, individualNameOne, individualNameOneTo);
        Set<OptionalIndividualName> optionNameOneSetTo = individualOptionNameOneTo.get_optionalIndividualName();
        for(OptionalIndividualName optionalIndividualNameTo : optionNameOneSetTo){
            String familyNameTo = optionalIndividualNameTo.getFamilyNames();
            System.out.println("familyNameTo==="+familyNameTo);
        }
        System.out.println("individualOptionNameOne==="+individualOptionNameOneTo.get_optionalIndividualName().size());

        *//*eliminate optionalName*//*
        IndividualName individualNameOneMore = new IndividualName("familyNameIndividualTo", "givenNameIndividualTo"); //这个是已经存在的optionName
        boolean rtnFlagEliminate = individualService.eliminateAlias(individualGetOne, individualNameOneMore);
        System.out.println("rtnFlagEliminate==="+rtnFlagEliminate);
        System.out.println("individualGetOne.get_optionalIndividualName() ==   "+individualGetOne.get_optionalIndividualName());

        *//*modify default name*//*
        IndividualName individualName = new IndividualName("familyNameDefault", "givenNameDefault");
        System.out.println(individualGetOne.get_defaultIndividualName().getFamilyNames());
        Individual individualDefaultName = individualService.modifyDefaultName(individualGetOne, individualName);
        System.out.println(individualDefaultName.get_defaultIndividualName().getFamilyNames());

    }*/
}
