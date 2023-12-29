package com.example.retro;

public class UltimateHash<T> {

    private T[] hashTable;

    public T[] getHashTable() {
        return hashTable;
    }

    public UltimateHash(int size) {
        this.hashTable = (T[]) new Object[size];
    }

    public int size(){
        return hashTable.length +1;
    }

    public T getElementFromPosition(int pos){
        if (hashTable[pos]!=null)
            return hashTable[pos];
        return null; //empty
    }


    public void displayHashTable(){
        System.out.println("Hash Table (using Linear Probing)\n=================");
        for(int i=0;i<hashTable.length;i++)
            System.out.println(i+". "+hashTable[i]);
    }


    public int hashFunction(T key) {
        int sum=0;
        for (int i = 0; i < key.toString().length(); i++) {
            sum=sum+key.toString().charAt(i);
        }
        return sum%hashTable.length;
    }

    public int add(T data) {
        int home=hashFunction(data),loc=home;
        do {
            if(hashTable[loc]==null) { //Free, so use it...
                hashTable[loc] = data;
                displayHashTable();
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
