package Weapons;

public class WeaponComponentFactory {

    public WeaponComponentFactory() {
    }

    public WeaponComponent create(String quickCode) {
        switch (quickCode) {
            case "DragonClaws":
                return new DragonClaws();
            case "AbbysalWhip":
                return new AbbysalWhip();
        }
        return null;
    }
}
