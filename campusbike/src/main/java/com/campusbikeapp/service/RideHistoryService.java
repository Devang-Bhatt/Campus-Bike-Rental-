package com.campusbikeapp.service;

import com.campusbikeapp.model.RideHistory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Web equivalent of Android RideHistoryActivity.loadDummyData()
 * Replace loadDummyData() with real API call when backend is ready
 */
@Service
public class RideHistoryService {

    private final ApiClient apiClient;

    public RideHistoryService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Fetch ride history for a user.
     * TODO: Replace dummy data with real API call:
     *   String json = apiClient.getAuth("/api/rides/history", token);
     *   return gson.fromJson(json, new TypeToken<List<RideHistory>>(){}.getType());
     */
    public List<RideHistory> getRideHistory(String token) {
        return loadDummyData();
    }

    /**
     * Same dummy data as Android RideHistoryActivity.java
     */
    private List<RideHistory> loadDummyData() {
        List<RideHistory> list = new ArrayList<>();
        list.add(new RideHistory("Library Station",  "Hostel Station",  "1.2 km", "Rs. 15.00", "Oct 24, 2023"));
        list.add(new RideHistory("Main Gate",         "Library Station", "0.8 km", "Rs. 10.00", "Oct 22, 2023"));
        list.add(new RideHistory("Sports Complex",    "Main Gate",       "2.1 km", "Rs. 25.00", "Oct 20, 2023"));
        list.add(new RideHistory("Hostel Station",    "Sports Complex",  "1.5 km", "Rs. 18.00", "Oct 18, 2023"));
        return list;
    }
}
