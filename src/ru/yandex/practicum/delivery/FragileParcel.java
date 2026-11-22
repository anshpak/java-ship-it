package ru.yandex.practicum.delivery;

public class FragileParcel extends Parcel implements Trackable {

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

    @Override
    public void reportStatus(String newLocation) {
        System.out.println("Хрупкая посылка " + getDescription() + " изменила местоположение на "
                + newLocation + ".");
    }
}
