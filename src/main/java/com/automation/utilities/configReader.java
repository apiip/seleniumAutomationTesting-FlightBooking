package com.automation.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class configReader {
    static Properties props = new Properties();

    public static String get(String key) {
        try {
            if (props.isEmpty()) {
                FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
                props.load(fis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props.getProperty(key);
    }


    // ----------------------------------------------------------------------
    // METODE PEMBANTU (HELPER METHODS) BARU UNTUK LOGIN
    // ----------------------------------------------------------------------

    /**
     * Mendapatkan username dari file konfigurasi.
     * Asumsi kunci (key) di config.properties adalah 'username'.
     */
    public static String getUsername() {
        // Memanggil fungsi 'get' yang sudah ada dengan kunci 'username'
        return get("username"); 
    }

    /**
     * Mendapatkan password dari file konfigurasi.
     * Asumsi kunci (key) di config.properties adalah 'password'.
     */
    public static String getPassword() {
        // Memanggil fungsi 'get' yang sudah ada dengan kunci 'password'
        return get("password");
    }

    // ----------------------------------------------------------------------
    // HELPER METHODS for Info Checkout
    // ----------------------------------------------------------------------

    // get origin and destination city from config properties.
    public static String getOriginCity() {
        return get("originCity"); 
    }

    // Mendapatkan origin city code dari file konfigurasi.
    public static String getOriginCityCode() {
        return get("originCityCode"); 
    }
    
        // Mendapatkan origin city dari file konfigurasi.
    public static String getDestCity() {
        return get("destCity"); 
    }

    // Mendapatkan origin city code dari file konfigurasi.
    public static String getDestCityCode() {
        return get("destCityCode"); 
    }
    
    public static String getbaseUrl() {
        return get("baseUrl"); 
    }

    public static String getFlightUrl() {
        return get("flightUrl"); 
    }

    // ----------------------------------------------------------------------
    // HELPER METHODS for booking details
    // ----------------------------------------------------------------------
    // method untuk set global variable FlightFormPage
    // Getter untuk properties
    public static Properties getProperties() {
        return props;
    }
}
