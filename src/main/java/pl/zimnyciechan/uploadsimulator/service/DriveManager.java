package pl.zimnyciechan.uploadsimulator.service;

import pl.zimnyciechan.uploadsimulator.model.objects.Drive;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class DriveManager {

    private static DriveManager instance;

    private Set<Drive> driveSet = new LinkedHashSet<>();

    public static DriveManager getInstance() {
        if (instance == null) {
            instance = new DriveManager();
        }
        return instance;
    }

    public DriveManager() {
        driveSet.add(new Drive("Drive1"));
        driveSet.add(new Drive("Drive2"));
        driveSet.add(new Drive("Drive3"));
        driveSet.add(new Drive("Drive4"));
        driveSet.add(new Drive("Drive5"));
    }

    public Drive getFreeDrive() {
        for (Drive drive : driveSet) {
            if (!drive.isBusy()) {
                return drive;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return driveSet.toString();
    }
}
