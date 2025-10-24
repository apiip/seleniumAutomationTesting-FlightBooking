# Selenium Web Automation - Flight Booking

## Deskripsi

Proyek ini adalah **Automation Testing Framework** untuk aplikasi pemesanan tiket pesawat menggunakan **Selenium WebDriver**.  
Framework ini dibangun dengan **Pendekatan Page Object Model (POM)** sehingga mudah dipelihara, scalable, dan mendukung berbagai kombinasi input.

---

## Representasi Utama

1. **Multiple Data & Data Chaining**
   - Dapat mengeksekusi **beberapa skenario secara berurutan** dengan data berbeda.
   - Mendukung **data chaining**, misal hasil pencarian sebelumnya bisa dipakai di langkah berikutnya.

2. **Kombinasi Category Penumpang, Seat, dan Maskapai**
   - Menangani **adult / child / infant** dengan input form dinamis.
   - Bisa memilih kategori seat dan maskapai sesuai kebutuhan skenario.
   - Looping otomatis untuk penumpang lebih dari 1, dengan index dinamis.

3. **Pendekatan POM (Page Object Model)**
   - Setiap halaman/komponen aplikasi direpresentasikan sebagai **class** tersendiri.
   - Selector dan method interaksi tersentralisasi → memudahkan maintenance.
   - Contoh:  
     - `FlightSearchPage` → search, select flight  
     - `BookingInfoPage` → input penumpang, passport, date of birth  
     - `CheckoutPage` → review dan validate order

---

## Catatan Penting

1. **Pastikan Kombinasi Input Penumpang dan Maskapai Sesuai**
2. **Pastikan Internet Stabil Karena Ada Dynamic Content**
3. **Sesuaikan Data Penumpang Pada Config Properties**

## Contoh Kombinasi Data Checkout

```
Examples:
    | adults | children | infants | flightCategory | airline            |
    | 1      | 0        | 0       | "economy"      | "Indonesia AirAsia"|
    | 2      | 1        | 1       | "economy"      | "Indonesia AirAsia"|
```

📄 Prerequisites

- Java 11+
- Selenium WebDriver 4.x
- TestNG / JUnit (sesuai framework)
- Maven / Gradle

```
👨‍💻 Made with ❤️ by
Author: Afif T R
Role: QA Automation Engineer
Contact: afiftabahr@gmail.com
```

