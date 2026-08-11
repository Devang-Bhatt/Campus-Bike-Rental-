# Campus Bike Web — Spring Boot + Thymeleaf

PC web version of the Campus Bike Android app.
Same Java code structure, same logic, same UI — runs in any browser.

## Project Structure

```
campus-bike-web/
├── pom.xml
└── src/main/
    ├── java/com/campusbikeapp/
    │   ├── CampusBikeApplication.java       ← Main (replaces Android MainActivity.java)
    │   ├── session/
    │   │   └── SessionManager.java          ← Same as Android SessionManager.java
    │   ├── model/
    │   │   ├── RideHistory.java             ← Same as Android RideHistory.java
    │   │   ├── LoginRequest.java
    │   │   ├── RegisterRequest.java
    │   │   └── BikeStation.java
    │   ├── service/
    │   │   ├── ApiClient.java               ← Same as Android ApiClient.java (OkHttp)
    │   │   └── RideHistoryService.java      ← Same as Android RideHistoryActivity.loadDummyData()
    │   └── controller/
    │       ├── LoginController.java         ← Replaces Android LoginActivity.java
    │       ├── HomeController.java          ← Replaces Android HomeActivity.java
    │       ├── QRScanController.java        ← Replaces Android QRScanActivity.java
    │       ├── ActiveRideController.java    ← Replaces Android ActiveRideActivity.java
    │       ├── RideHistoryController.java   ← Replaces Android RideHistoryActivity.java
    │       ├── ProfileController.java       ← Replaces Android ProfileActivity.java
    │       └── SosController.java          ← Replaces Android SosActivity.java
    └── resources/
        ├── application.properties
        ├── templates/
        │   ├── login.html                   ← LoginActivity UI
        │   ├── register.html               ← RegisterActivity UI
        │   ├── home.html                   ← HomeActivity UI (with Leaflet/OSM map)
        │   ├── scan.html                   ← QRScanActivity UI (with real webcam QR)
        │   ├── active_ride.html            ← ActiveRideActivity UI (live timer)
        │   ├── history.html               ← RideHistoryActivity + Adapter UI
        │   ├── profile.html               ← ProfileActivity UI
        │   └── sos.html                   ← SosActivity UI
        └── static/
            ├── css/main.css
            └── js/main.js
```

## How to Run

### Prerequisites
- Java 17+
- Maven 3.8+

### Steps

```bash
# 1. Go to project folder
cd campus-bike-web

# 2. Build
mvn clean package

# 3. Run
mvn spring-boot:run
```

Open browser: **http://localhost:8081**

### Login credentials (demo)
Any student ID + valid email + 6+ char password works while the real backend is not wired.

## Connecting to Your Android Backend (http://localhost:8080)

All API call points are marked with `// TODO:` comments in each controller.
To wire up the real backend, uncomment the `apiClient.post(...)` calls:

| Controller            | TODO location             | Android equivalent            |
|-----------------------|---------------------------|-------------------------------|
| `LoginController`     | `doLogin()`               | `LoginActivity` btnLogin      |
| `LoginController`     | `doRegister()`            | `RegisterActivity` btnRegister|
| `QRScanController`    | `unlockBike()`            | `QRScanActivity.handleBikeScanned()` |
| `ActiveRideController`| `startRide()`, `endRide()`| `ActiveRideActivity`          |
| `ProfileController`   | `addMoney()`              | `ProfileActivity.payUsingUpi()`|
| `SosController`       | `sendSos()`               | `SosActivity` btnSendSOS      |

## Features

- ✅ Login + Register with same validation as Android
- ✅ OpenStreetMap with Leaflet (same as Android osmdroid)
- ✅ Same bike/station markers as Android
- ✅ Live ride timer + cost calculation (same formula as Android)
- ✅ Webcam QR scanner (jsQR) + manual entry
- ✅ Ride history with same dummy data as Android
- ✅ Profile page with wallet + Add Money modal
- ✅ SOS page with pulsing button + emergency contacts
- ✅ Session management (HttpSession ≡ Android SharedPreferences)
- ✅ Same ApiClient (OkHttp) pointing to http://localhost:8080
