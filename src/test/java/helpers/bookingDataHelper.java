package helpers;

import java.util.Properties;

import pages.bookingInfoPage;
import pages.discoPage;

public class bookingDataHelper {
    // public static bookingInfoPage bookingInfoPage;
    public static void setBookingInfoToGlobal(Properties prop) {
        // Contact
        bookingInfoPage.contactFirstName = prop.getProperty("contact.firstName");
        bookingInfoPage.contactLastName = prop.getProperty("contact.lastName");
        bookingInfoPage.contactEmail = prop.getProperty("contact.email");
        bookingInfoPage.contactPhone = prop.getProperty("contact.phone");
        bookingInfoPage.isMale = prop.getProperty("contact.gender");

        // Passenger
        bookingInfoPage.selectedFlightName = prop.getProperty("passenger.firstName");
        bookingInfoPage.passengerLastName = prop.getProperty("passenger.lastName");
        bookingInfoPage.dobDay = prop.getProperty("passenger.dobDay");
        bookingInfoPage.dobYearAdult = prop.getProperty("passenger.dobYear");
        bookingInfoPage.dobYearChild = prop.getProperty("passenger.dobYearChild");
        bookingInfoPage.dobYearInfant = prop.getProperty("passenger.dobYearInfant");
        bookingInfoPage.dopYear = prop.getProperty("passenger.dopYear");
        bookingInfoPage.passportNumber = prop.getProperty("passenger.passport");
        bookingInfoPage.birthMonth = prop.getProperty("passenger.birthMonth");
        bookingInfoPage.nationality = prop.getProperty("passenger.nationality");
    }

    public static void flightSchedule(Properties prop) {
        // discoPage.urlFLight = prop.getProperty("flightUrl");
        discoPage.origin = prop.getProperty("originCity");
        discoPage.dest = prop.getProperty("destCity");
        discoPage.codeOrigin = prop.getProperty("originCityCode");
        discoPage.codeDest = prop.getProperty("destCityCode");
        
    }

    // for manipulation total passengers
    // public static void totalPassengers(int adults, int children, int infants) {
    //     // discoPage.urlFLight = prop.getProperty("flightUrl");
    //     globalData.adults = adults;
    //     globalData.children = children;
    //     globalData.infants = infants;
    //     globalData.selectedFlightIndex = 0;
    //     globalData.selectedFlightName = null;
    //     globalData.selectedFlightPrice = null;

    // }

}
