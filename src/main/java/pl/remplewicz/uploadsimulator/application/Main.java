package pl.remplewicz.uploadsimulator.application;

import pl.remplewicz.uploadsimulator.actions.UploadFileAction;
import pl.remplewicz.uploadsimulator.objects.Client;
import pl.remplewicz.uploadsimulator.objects.Drive;
import pl.remplewicz.uploadsimulator.resources.Storage;

import java.util.List;

import static pl.remplewicz.uploadsimulator.util.SimulationTools.testFiles;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello world!");

        final Client c1 = new Client("tester1", new Storage(List.of(testFiles[0], testFiles[1])));
        final Client c2 = new Client("tester2", new Storage(List.of(testFiles[2], testFiles[3])));
        final Client c3 = new Client("tester3", new Storage(List.of(testFiles[2], testFiles[3], testFiles[4])));

        final Drive drive = new Drive("myDrive");


        UploadFileAction testAction = new UploadFileAction("Action1", c3, drive);
        UploadFileAction testAction2 = new UploadFileAction("Action2", c2, drive);
        UploadFileAction testAction3 = new UploadFileAction("Action3", c1, drive);
        Thread testThread = new Thread(testAction);
        Thread testThread2 = new Thread(testAction2);
        Thread testThread3 = new Thread(testAction3);
        testThread.start();
        testThread2.start();
        testThread3.start();
        testThread.join();
        testThread2.join();
        testThread3.join();
        System.out.println(drive);
    }
}
