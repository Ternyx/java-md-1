package com.github.ternyx.models;

import java.util.Objects;
import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;
import com.github.ternyx.ifaces.IdNumberGenerator;

/**
 * VipPassenger
 */
public class VipPassenger extends Passenger implements IdNumberGenerator {
    private static int vipNrCounter = 0;
    private int vipNr;
    private String loungeCardNr;

    public VipPassenger(String name, String surname, IdenType idenType, Nationality nationality,
            String idenNr, boolean isAdult, String extraNeeds, String loungeCardNr) {
        super(name, surname, idenType, nationality, idenNr, isAdult, extraNeeds);

        generateNr();
        generateExtraNeeds();
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

    private void generateExtraNeeds() {
        this.setExtraNeeds(this.getExtraNeeds() + " VIP");
    }

    @Override
    public void generateNr() {
        this.vipNr = vipNrCounter;
        ++vipNrCounter;
    }
}
