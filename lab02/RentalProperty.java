/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 17 Jun 2020
 * Course: CSCI2251
 *
 * Represents a property with rent, the number of bedrooms, and the id.
 */
import java.util.Formatter;
import java.text.DecimalFormat;

public abstract class RentalProperty implements Payment, Comparable<RentalProperty> {

    private int bedrooms;
    private String id;
    protected double rent;

    public RentalProperty(String id, int bedrooms, double rent) {
        this.bedrooms = bedrooms;
        this.id = id;
        this.rent = rent;
    }

    /**
     * Constructs from a record from the rentalDB file.
     */
    public RentalProperty(String record) {
        String[] split = record.split(" ");
        if (split.length < 5) {
            throw new IllegalArgumentException("Record does not contain all fields.");
        }
        this.id = split[2]; 
        this.bedrooms = Integer.parseInt(split[3]);
        this.rent = Double.parseDouble(split[4]);
    }
    
    /**
     * Writes the property to a file.
     *
     * @param rentDb a formatter writing to the rentalDB file.
     * @param sequenceNumber the number to put before the property
     */
    public void updateRent(Formatter rentDb, int sequenceNumber) {
        rentDb.format("%d %c %s %d %.2f%n", sequenceNumber, this.id.charAt(0),
                this.id, this.bedrooms, this.rent);
    }

    /**
     * Outputs property information.
     */
    public void printRent() {
        DecimalFormat df = new DecimalFormat(",###.00");
        String rentString = "$" + df.format(this.rent);
        System.out.printf("   %s%-15c%10s%n", this.id, ' ', rentString);
    }

    @Override
    public int compareTo(RentalProperty r) {
        return this.id.compareTo(r.id);
    }
    
    /**
     * Updates the rental price.
     */
    @Override
    public abstract void calculatePayment();
}
