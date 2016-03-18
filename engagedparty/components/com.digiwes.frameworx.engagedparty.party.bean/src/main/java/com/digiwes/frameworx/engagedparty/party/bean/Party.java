package com.digiwes.frameworx.engagedparty.party.bean;

import com.digiwes.frameworx.common.basetype.TimePeriod;

/**
 * Created by nisx on 16-3-15.
 */
public class Party {
    private  String partyId;
    private TimePeriod validFor;
    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
    public TimePeriod getValidFor() {
        return validFor;
    }

    public void setValidFor(TimePeriod validFor) {
        this.validFor = validFor;
    }

}
