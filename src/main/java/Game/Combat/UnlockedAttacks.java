//package Game.Combat;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//public class UnlockedAttacks {
//
//    private Map<String, Attack> availableAttacks = new HashMap<>();
//
//    public UnlockedAttacks() {
//    }
//
//    public void addWeaponComponent(WeaponComponent unlockedWeapon) {
//        availableAttacks.putAll(unlockedWeapon.getAttackList());
//    }
//
//    public void removeWeaponComponent(String quickCode) {
//        availableAttacks.remove(quickCode);
//    }
//
//    public boolean hasAttack(String quickCode) {
//        return availableAttacks.containsKey(quickCode);
//    }
//
//    public AttackResultContext attack(String quickCode) {
//        return availableAttacks.get(quickCode).attack();
//    }
//
//
//    public Set<String> getAvailableAttacks() {
//        return availableAttacks.keySet();
//    }
//
//}
