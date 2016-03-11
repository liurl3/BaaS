package com.digiwes.tryout.odata.resource;

import com.digiwes.frameworx.engagedparty.party.bean.Individual;
import org.apache.olingo.commons.api.data.Entity;

import java.util.List;

/**
 * Created by nisx on 16-3-10.
 */
public interface IndividualResource {
    public Entity createParty(Entity requestEntity) throws Exception;
    public Entity updateParty(Entity requestEntity);
    public List<Individual> retrieveParty();
    public Entity retrievePartyById(String partyId) throws Exception;
    public boolean delParty(String partyId);
}
