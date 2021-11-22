package hellojpa;

import java.util.Hashtable;

public class ValueMain {
    public static void main(String[] args) {
        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");
        Address address3 = new Address("city", "street", "10000");

        int hashCode1 = address1.hashCode();
        int hashCode2 = address2.hashCode();

        boolean isSameHashcode = hashCode1 == hashCode2;

        System.out.println("isSameHashcode = " + isSameHashcode);

        Hashtable<Address, Address> addressHashtable = new Hashtable<>();

        addressHashtable.put(address1, address1);
        addressHashtable.put(address2, address1);

        int size = addressHashtable.size();
        System.out.println("size = " + size);

        boolean isContained = addressHashtable.containsValue(address3);
        System.out.println("isContained = " + isContained);
    }
}
