package com.example.retro;

public class UltimateHash<T> {

    private T[] hashTable;

    public T[] getHashTable() {
        return hashTable;
    }

    public UltimateHash(int size) {
        this.hashTable = (T[]) new Object[size];//downcast object array to T array
    }

    public int size(){
        return hashTable.length;
    }

    public T getElementFromPosition(int pos){
        if (hashTable[pos]!=null)
            return hashTable[pos];
        return null; //empty
    }

    public void replace(T t, int i){//for editing
        hashTable[i]=t;//assigning element at position i with new object
    }

    public void clear(){
        hashTable = (T[]) new Object[size()];//replacing current table with empty one of same size
    }

    public void delete(int i){
        hashTable[i]=null;//making space available for new object
    }


    public void deleteElement(T data) {
        int home = hashFunction(data.toString());
        int loc = home;

        do {
            T currentData = hashTable[loc];
            if (currentData != null && currentData.equals(data)) {
                // Found the element, delete it
                hashTable[loc] = null;
                return;
            } else {
                // Not found, so probe ahead by 1 with wrap-round
                loc = (loc + 1) % hashTable.length;
            }
        } while (loc != home);
    }

    public void replaceElement(T newData, T oldData) {
        int home = hashFunction(oldData.toString());
        int loc = home;

        do {
            T currentData = hashTable[loc];
            if (currentData != null && currentData.equals(oldData)) {
                // Found the element to replace, update it with the new value
                hashTable[loc] = newData;
                return;
            } else {
                // Not found, so probe ahead by 1 with wrap-round
                loc = (loc + 1) % hashTable.length;
            }
        } while (loc != home);
    }

    public void displayHashTable(){//for debugging
        System.out.println("Hash Table");
        for(int i=0;i<hashTable.length;i++)
            System.out.println(i+". "+hashTable[i]);
    }


    public int hashFunction(String key) {
        int sum=0;
        for (int i = 0; i < key.length(); i++) {
            sum=sum+key.charAt(i);
        }
        return sum%hashTable.length;//getting sum of hall characters and getting the modulus by hashtable length
    }

    public int add(T data) {

        boolean needRehash=true;

        for (T t : hashTable)
            if (t==null) {//checking if there are any empty spaces
                needRehash = false;
                break;
            }
        if (needRehash){
            rehash();//rehash if there are no empty spaces
            System.out.println("rehashing");
        }

        int home=hashFunction(data.toString()),loc=home;
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

    private void rehash() {

        int newSize = hashTable.length * 2; // double the size for simplicity
        T[] newHashTable = (T[]) new Object[newSize];

        // Rehash existing elements
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                T data = hashTable[i];
                newHashTable[i]=data;
            }
        }
        hashTable=newHashTable;
    }

    public T find(T data) {
        int home = hashFunction(data.toString());
        int loc = home;

        do {
            T currentData = hashTable[loc];
            if (currentData != null && currentData.equals(data)) {
                // Found the element
                return currentData;
            } else {
                // Not found, so probe ahead by 1 with wrap-round
                loc = (loc + 1) % hashTable.length;
            }
        } while (loc != home);

        // Element not found
        return null;
    }

    public int population(){
        int sum = 0;
        for (int i = 0;i<size();i++)
            if (getElementFromPosition(i)!= null) sum++;
        return sum;
    }



}
