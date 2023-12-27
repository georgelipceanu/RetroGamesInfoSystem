package com.example.retro;

public class UltimateHash {

    int[] hashTable;

    public UltimateHash(int size) {
        hashTable=new int [size];
    }

    public int hashFunction(int key) {
        return key%hashTable.length;
    }

    public void displayHashTable(){
        System.out.println("Hash Table (using Linear Probing)\n=================");
        for(int i=0;i<hashTable.length;i++)
            System.out.println(i+". "+hashTable[i]);
    }

    public int add(int data) {
        int home=hashFunction(data),loc=home;
        do {
            if(hashTable[loc]==0) { //Free, so use it...
                hashTable[loc] = data;
                return loc;
            }
            else {
                //Full, so probe ahead by 1 with wrap round
                System.out.println(loc+" full so probing ahead by 1...");
                loc=(loc+1)%hashTable.length;
            }

        }while(loc!=home);
        return -1; //Failed!!!!
    }

}
