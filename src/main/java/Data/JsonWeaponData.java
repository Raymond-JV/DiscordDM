package Data;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class JsonWeaponData {
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private int price;
    @SerializedName("maxHit")
    private int maxHit;
    @SerializedName("accuracy")
    private double accuracy;
    @SerializedName("spec")
    private int spec;
    @SerializedName("quickCodes")
    private String[] quickCodes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public void setMaxHit(int maxHit) {
        this.maxHit = maxHit;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getSpec() {
        return spec;
    }

    public void setSpec(int spec) {
        this.spec = spec;
    }

    public String[] getQuickCodes() {
        return quickCodes;
    }

    public void setQuickCodes(String[] quickCodes) {
        this.quickCodes = quickCodes;
    }


    public JsonWeaponData()
    {}

    @Override
    public String toString() {
        return "WeaponAttributes{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", maxHit=" + maxHit +
                ", accuracy=" + accuracy +
                ", spec=" + spec +
                ", quickCodes=" + Arrays.toString(quickCodes) +
                '}';
    }
}
