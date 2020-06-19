/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 17 Jun 2020
 * Course: CSCI2251
 *
 * Single family property with 4% increase
 */

public class SingleFamilyRental extends RentalProperty {
    public SingleFamilyRental(String id, int bedrooms, double rent) {
        super(id, bedrooms, rent);
    }

    public SingleFamilyRental(String record) {
        super(record);
    }
    
    @Override
    public void calculatePayment() {
        this.rent = this.rent + (0.04 * this.rent);
    }   
}
