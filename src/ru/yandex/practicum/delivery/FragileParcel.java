package ru.yandex.practicum.delivery;

public class FragileParcel extends Parcel{

    private static final int BASE_DELIVERY_COST = 4;

    public FragileParcel(String description, double weight, String deliveryAddress, byte sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.println("Посылка " + getDescription() + " обёрнута в защитную плёнку.");
        super.packageItem();
    }

    @Override
    public int getBaseDeliveryCost() {
        return BASE_DELIVERY_COST;
    }
}
