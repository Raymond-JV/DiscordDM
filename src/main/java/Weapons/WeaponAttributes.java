package Weapons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeaponAttributes {



    private int maxHit;
    private int spec;
    private double accuracy;
    private List<String> quickCodes = new ArrayList<>();

    public WeaponAttributes() {
    }


    public void setMaxHit(int maxHit) {
        this.maxHit = maxHit;
    }

    public void setSpec(int spec) {
        this.spec = spec;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void addQuickCode(String ... attackCodes) {
        this.quickCodes.addAll(Arrays.asList(attackCodes));
    }


    public int getMaxHit() {
        return maxHit;
    }

    public int getSpec() {
        return spec;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public List<String> getQuickCodes() {
        return quickCodes;
    }

    public String toString() {
        StringBuilder codes = new StringBuilder();
        for (String s: quickCodes ) {
            codes.append(s).append(" ");
        }
        return String.format("Codes:[%s], Accuracy:[%f], Max Hit:[%d]%n", codes.toString(), accuracy, maxHit);
    }
}