package pl.zimnyciechan.uploadsimulator.service;

import lombok.Setter;
import pl.zimnyciechan.uploadsimulator.actions.UploadFileAction;
import pl.zimnyciechan.uploadsimulator.model.objects.Client;
import pl.zimnyciechan.uploadsimulator.model.objects.Drive;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UploadManager {

    private static UploadManager instance;
    private Set<UploadFileAction> actionSet = new HashSet<>();

    UploadWatcher watcher;
    private int actionCounter = 0;

    public UploadManager() {
        watcher = new UploadWatcher();
        watcher.start();
    }

    public void stopWatcher() {
        watcher.setRunning(false);
    }

    public static UploadManager getInstance() {
        if (instance == null) {
            instance = new UploadManager();
        }
        return instance;
    }

    public void addClientToUpload() {
        QueueManager queueManager = QueueManager.getInstance();
        DriveManager driveManager = DriveManager.getInstance();
        Client nextClient = queueManager.getNextClient();
        if (nextClient == null) {
            System.out.println("No clients in Queue");
            return;
        }
        Drive freeDrive = driveManager.getFreeDrive();
        if (freeDrive == null) {
            System.out.println("No free Drives");
            return;
        }
        freeDrive.setBusy(true);
        UploadFileAction uploadAction = new UploadFileAction("Upload Action #" + actionCounter, nextClient, freeDrive);
        actionCounter++;
        uploadAction.start();
        actionSet.add(uploadAction);
    }

    static class UploadWatcher extends Thread {

        @Setter
        boolean running = true;

        @Override
        public void run() {
            while (running) {
                DriveManager.getInstance().printDrivesState();
                if (UploadManager.getInstance().actionSet.size() < 5) {
                    UploadManager.getInstance().addClientToUpload();
                }
                Iterator<UploadFileAction> iterator = UploadManager.getInstance().actionSet.iterator();
                while (iterator.hasNext()) {
                    UploadFileAction action = iterator.next();
                    action.printInfo();
                    if (!action.isAlive()) {
                        iterator.remove();
                        action.getReciever().setBusy(false);
//                        Client sender = action.getSender();
//                        if (!sender.getStorage().isEmpty()) {
//                            QueueManager.getInstance().addClientToQueue(sender);
//                        }
                    }
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
