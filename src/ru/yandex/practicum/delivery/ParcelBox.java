package ru.yandex.practicum.delivery;

import java.util.List;
import java.util.ArrayList;

public class ParcelBox<T extends Parcel> {

    private final List<T> box = new ArrayList<>();
    private double availableWeight;

    public ParcelBox(double availableWeight) {
        this.availableWeight = availableWeight;
    }

    public double getAvailableWeight() {
        return availableWeight;
    }

    public void addParcel(T parcel) {
        if (availableWeight - parcel.getWeight() < 0) {
            System.out.println("Невозможно добавить посылку " + parcel.getDescription() + " в коробку.");
            System.out.println("Превышен максимально возможный вес");
            return;
        }
        box.add(parcel);
        availableWeight -= parcel.getWeight();
    }

    public List<T> getAllParcels() {
        return box;
    }

}
