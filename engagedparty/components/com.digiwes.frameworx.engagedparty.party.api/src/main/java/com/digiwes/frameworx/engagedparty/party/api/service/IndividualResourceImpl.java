package com.digiwes.frameworx.engagedparty.party.api.service;

import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import com.digiwes.frameworx.engagedparty.party.interfaces.IndividualFactory;
import net.sf.json.JSONObject;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

/**
 * Created by liurl3 on 2016/3/9.
 */
@Component(name = "IndividualResource" , immediate = true)
@Service(value = IndividualFactory.class)
public class IndividualResourceImpl implements IndividualFactory {
    @Override
    public Individual fromString(String individualParam) {
        JSONObject jsonObject = JSONObject.fromObject(individualParam);
        Individual individual = (Individual)JSONObject.toBean(jsonObject,Individual.class);
        return individual;
    }

    @Override
    public String toString(Individual individual) {
        JSONObject jsonObject = JSONObject.fromObject(individual);
        String jsonString = jsonObject.toString();
        return jsonString;
    }
}
