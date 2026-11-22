package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> allTrackableParcels = new ArrayList<>();
    private static final int MAX_PARCELS_WEIGHT_FOR_BOX = 100;
    private static ParcelBox<StandardParcel> standardParcelsBox = new ParcelBox<>(MAX_PARCELS_WEIGHT_FOR_BOX);
    private static ParcelBox<PerishableParcel> perishableParcelsBox = new ParcelBox<>(MAX_PARCELS_WEIGHT_FOR_BOX);
    private static ParcelBox<FragileParcel> fragileParcelsBox = new ParcelBox<>(MAX_PARCELS_WEIGHT_FOR_BOX);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    trackParcels();
                    break;
                case 5:
                    showParcelTypes();
                    int typeChoice = Integer.parseInt(scanner.nextLine());
                    switch (typeChoice) {
                        case 1:
                            showBoxContent(standardParcelsBox);
                            break;
                        case 2:
                            showBoxContent(perishableParcelsBox);
                            break;
                        case 3:
                            showBoxContent(fragileParcelsBox);
                            break;
                        default:
                            System.out.println("Выбран неправильный тип посылки.");
                    }
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Отследить хрупкие посылки");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void showParcelTypes() {
        System.out.println("Выберите тип посылки:");
        System.out.println("1 — Стандартная посылка.");
        System.out.println("2 — Скоропортящаяся посылка.");
        System.out.println("3 — Хрупкая посылка.");
    }

    private static void addParcel() {
        System.out.println("Введите описание посылки:");
        String description = scanner.nextLine();
        System.out.println("Введите вес посылки:");
        int weight = Integer.parseInt(scanner.nextLine());
        if (weight <= 0) {
            System.out.println("Вес должен быть положительным.");
            System.out.println("Посылка " + description + " добавлена не будет .");
            return;
        }
        System.out.println("Введите адрес посылки:");
        String deliveryAddress = scanner.nextLine();
        System.out.println("Введите день отправки посылки:");
        byte sendDay = Byte.parseByte(scanner.nextLine());

        showParcelTypes();
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
                if (standardParcelsBox.addParcel(standardParcel)) {
                    allParcels.add(standardParcel);
                }
                break;
            case 2:
                System.out.println("Введите срок годности посылки:");
                byte timeToLive = Byte.parseByte(scanner.nextLine());
                PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay,
                        timeToLive);
                if (perishableParcelsBox.addParcel(perishableParcel)) {
                    allParcels.add(perishableParcel);
                }
                break;
            case 3:
                FragileParcel fragileParcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
                if (fragileParcelsBox.addParcel(fragileParcel)) {
                    allTrackableParcels.add(fragileParcel);
                    allParcels.add(fragileParcel);
                }
                break;
            default:
                System.out.println("Выбрали неверный тип посылки.");
        }
    }

    private static void sendParcels() {
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        int sum = 0;
        for (Parcel parcel : allParcels) {
            sum += parcel.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость всех доставок: " + sum + ".");
    }

    private static void trackParcels() {
        if (allTrackableParcels.isEmpty()) {
            System.out.println("Список хрупких посылок пуст.");
        }
        for (Trackable trackableParcel : allTrackableParcels) {
            System.out.println("Введите новое местоположение для посылки "
                    + ((Parcel) trackableParcel).getDescription() + ":");
            String newLocation = scanner.nextLine();
            trackableParcel.reportStatus(newLocation);
        }
    }

    private static void showBoxContent(ParcelBox<? extends Parcel> box) {
        if (box.getAllParcels().isEmpty()) {
            System.out.println("Коробка пуста.");
            return;
        }
        for (Parcel parcel : box.getAllParcels()) {
            System.out.println(parcel.getDescription());
        }
    }

}

