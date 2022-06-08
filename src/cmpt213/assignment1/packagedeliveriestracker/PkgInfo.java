package cmpt213.assignment1.packagedeliveriestracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class for holding package information
 *
 * @author Omansh
 */

public class PkgInfo {


    //Private fields
    private final String name;
    private final String notes;
    private final double price;
    private final double weight;
    private final LocalDateTime deliveryDate;
    private boolean isDelivered;

    //Constructors

    /**
     * Constructor to create a package that takes each part of date time individually
     *
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param year
     * @param month
     * @param dayOfMonth
     * @param hour
     * @param minute
     */
    public PkgInfo(String name, String notes, double price, double weight, int year, int month, int dayOfMonth, int hour, int minute) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.weight = weight;
        this.isDelivered = false;
        this.deliveryDate = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }

    /**
     * Constructor to create a package that takes date time as a single variable
     *
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param deliveryDate LocalDateTime variable
     */
    public PkgInfo(String name, String notes, double price, double weight, LocalDateTime deliveryDate) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.weight = weight;
        this.isDelivered = false;
        this.deliveryDate = deliveryDate;
    }

    //Public methods

    /**
     * Returns delivery date
     *
     * @return delivery date
     */
    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Sets delivered
     *
     */
    public void setDelivered() {
        isDelivered = true;
    }

    /**
     * Returns delivery status
     *
     * @return delivery status
     */
    public boolean isDelivered() {
        return isDelivered;
    }

    /**
     * Returns package name
     *
     * @return package name
     */
    public String getName() {
        return name;
    }

    /**
     * Converts package to string
     *
     * @return toString
     */
    @Override
    public String toString() {

        //Turning boolean into string value
        String delivered = "No";
        if (isDelivered) {
            delivered = "Yes";
        }

        //Formatting date time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = deliveryDate.format(formatter);

        //Duration between delivery date and current date
        Duration duration = Duration.between(LocalDateTime.now(), deliveryDate);
        String durationInDays = duration.toDays() + " days";

        return "Package: " + name + "\n" + "Notes: " + notes + "\n" +
                "Price: $" + String.format("%.2f",price) + "\n" + "Weight: " + String.format("%.2f",weight) + "kg\n" +
                "Has been delivered: " + delivered + "\n" + "Delivery date: "
                + formatDateTime + "\n" + "Days until delivery date: " + durationInDays + "\n";
    }

}
