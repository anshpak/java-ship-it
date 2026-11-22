package ru.yandex.practicum.delivery;

public class StandardParcel extends Parcel{

    public static final int BASE_DELIVERY_COST = 2;

    public StandardParcel(String description, double weight, String deliveryAddress, byte sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public int getBaseDeliveryCost() {
        return BASE_DELIVERY_COST;
    }
}
