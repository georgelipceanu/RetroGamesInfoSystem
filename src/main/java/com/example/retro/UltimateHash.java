package com.example.retro;

public class UltimateHash<T> {

    private T[] hashTable;

    public T[] getHashTable() {
        return hashTable;
    }

    public UltimateHash(int size) {
        this.hashTable = (T[]) new Object[size];
    }

    public int hashFunction(T key) {
        int sum=0;
        for (int i = 0; i < key.toString().length(); i++) {
            if(Character.isDigit(key.toString().charAt(i)))
                sum=sum+Character.getNumericValue(key.toString().charAt(i));
        }
        return sum%hashTable.length;
    }

    public int add(T data) {
        int home=hashFunction(data),loc=home;
        do {
            if(hashTable[loc]==null) { //Free, so use it...
                hashTable[loc] = data;
                return loc;
            }
            else {
                //Full, so probe ahead by 1 with wrap round
                System.out.println(loc+" full so probing ahead by 1...");
                loc=(loc+1)%hashTable.length;
            }

        } while(loc!=home);
        return -1; //Failed!!!!
    }

}
