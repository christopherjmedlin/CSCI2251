/*
 * Author: Christopher Medlin
 * Email: cmedlin@cnm.edu
 * Date: 17 Jun 2020
 * Course: CSCI2251
 *
 * Updates rental costs for apartments and single family homes.
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Formatter;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;

public class RentalDueTest { 
    public static void main(String[] args) {
        String filename = args.length == 1 ? args[0] : "rentalDB.txt";
        RentalProperty[] properties = loadProperties(filename);

        try (Formatter out = new Formatter(filename)) {
            for (int i = 0; i < properties.length; i++) {
                // update the rent
                properties[i].calculatePayment();
                // write to file
                properties[i].updateRent(out, i+1);
            }
            out.close();
        } catch (FileNotFoundException e) {
            System.err.printf("File %s does not exist.", filename);
            System.exit(1);
        }

        printProperties(properties);
    }

    // Loads every line from the rentalDB file
    private static RentalProperty[] loadProperties(String filename) {
        ArrayList<RentalProperty> properties = new ArrayList<RentalProperty>();

        try (Scanner file = new Scanner(new File(filename))) {
            while (file.hasNext()) {
                String record = file.nextLine();
                if (record.charAt(2) == 'S') {
                    properties.add(new SingleFamilyRental(record));
                } else {
                    properties.add(new ApartmentRental(record));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.printf("File %s does not exist.", filename);
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.print(
                "Invalid rentDB file. One or more records are missing or contain invalid fields."
            );
            System.exit(1);
        }

        return properties.toArray(new RentalProperty[0]);
    }

    private static void printProperties(RentalProperty[] properties) {
        // sort by ID
        Arrays.sort(properties);

        System.out.println("Single Family Rental Summary:");
        System.out.println("House ID Number          Rental Due");
        System.out.println("===============          ==========");
        // print single family properties
        for (RentalProperty property : properties) {
            if (property instanceof SingleFamilyRental) {
                property.printRent();
            }
        }
        
        System.out.println("\nApartment Rental Summary:");
        System.out.println("Apartment ID No.         Rental Due");
        System.out.println("================         ==========");
        // print apartment properties
        for (RentalProperty property : properties) {
            if (property instanceof ApartmentRental) {
                property.printRent();
            }
        }
    }
}
