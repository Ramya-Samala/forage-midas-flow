package com.jpmc.midascore;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class UserPopulator {
    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private DatabaseConduit databaseConduit;

    public void populate() {
        //String[] userLines = fileLoader.loadStrings("/test_data/lkjhgfdsa.hjkl");
        String[] userLines = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/test_data/lkjhgfdsa.hjkl")))
                .lines()
                .toArray(String[]::new);
        for (String userLine : userLines) {
            String cleanLine = userLine.trim().replaceAll("\\R", "");//added by Ramya
            String[] userData = cleanLine.split(", ");
            UserRecord user = new UserRecord(userData[0], Float.parseFloat(userData[1]));
            databaseConduit.save(user);
        }
    }
}
