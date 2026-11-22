package ru.yandex.practicum.delivery;

import java.util.List;
import java.util.ArrayList;

public class ParcelBox<T extends Parcel> {

    private final List<T> parcelsList = new ArrayList<>();
    private int availableWeight;

    public ParcelBox(int availableWeight) {
        this.availableWeight = availableWeight;
    }

    public int getAvailableWeight() {
        return availableWeight;
    }

    public boolean addParcel(T parcel) {
        if (availableWeight - parcel.getWeight() < 0) {
            System.out.println("Невозможно добавить посылку " + parcel.getDescription() + " в коробку.");
            System.out.println("Превышен максимально возможный вес");
            return false;
        }
        parcelsList.add(parcel);
        availableWeight -= parcel.getWeight();
        return true;
    }

    public List<T> getAllParcels() {
        return List.copyOf(parcelsList);
    }

    public void clear() {
        parcelsList.clear();
    }

}
