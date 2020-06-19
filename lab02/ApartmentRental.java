/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 17 Jun 2020
 * Course: CSCI2251
 *
 * Apartment property with 8% increase
 */

public class ApartmentRental extends RentalProperty {
    public ApartmentRental(String id, int bedrooms, double rent) {
        super(id, bedrooms, rent);
    }
    
    public ApartmentRental(String record) {
        super(record);
    }

    @Override
    public void calculatePayment() {
        this.rent = this.rent + (0.08 * this.rent);
    }   
}
