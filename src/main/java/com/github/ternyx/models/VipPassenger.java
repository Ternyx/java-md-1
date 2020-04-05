package com.github.ternyx.models;

import java.util.Objects;
import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;
import com.github.ternyx.ifaces.IdNumberGenerator;

/**
 * VipPassenger
 */
public class VipPassenger extends Passenger implements IdNumberGenerator {
    private static final String DEFAULT_NEEDS = "VIP";
    private static int vipNrCounter = 0;
    private int vipNr;
    private String loungeCardNr;

    public VipPassenger(String name, String surname, Nationality nationality, IdenType idenType, 
            String idenNr, boolean isAdult, String extraNeeds, String loungeCardNr) {
        super(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds);

        generateNr();
        this.loungeCardNr = Objects.requireNonNull(loungeCardNr);
    }

    public int getVipNr() {
        return vipNr;
    }

    public String getLoungeCardNr() {
        return loungeCardNr;
    }

    public void setLoungeCardNr(String loungeCardNr) {
        this.loungeCardNr = Objects.requireNonNull(loungeCardNr);
    }

    @Override
    public void generateExtraNeeds() {
        this.setExtraNeeds(DEFAULT_NEEDS);
    }

    @Override
    public void generateNr() {
        this.vipNr = vipNrCounter;
        ++vipNrCounter;
    }
}
