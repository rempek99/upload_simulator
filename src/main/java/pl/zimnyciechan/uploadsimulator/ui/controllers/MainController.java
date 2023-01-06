package pl.zimnyciechan.uploadsimulator.ui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import pl.zimnyciechan.uploadsimulator.model.objects.Client;
import pl.zimnyciechan.uploadsimulator.model.resources.FileResource;
import pl.zimnyciechan.uploadsimulator.model.resources.Storage;
import pl.zimnyciechan.uploadsimulator.service.DriveManager;
import pl.zimnyciechan.uploadsimulator.service.QueueManager;
import pl.zimnyciechan.uploadsimulator.service.UploadFileAction;
import pl.zimnyciechan.uploadsimulator.service.UploadManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainController implements Initializable {

    private static final long REFRESH_RATE = 500;
    @FXML
    private TableView<Client> queueTable;

    @FXML
    private TableView<UploadFileAction> uploadTable;

    private final QueueManager queueManager = QueueManager.getInstance();

    private final UploadManager uploadManager = UploadManager.getInstance();

    @FXML
    public void buttonClick(ActionEvent actionEvent) {
//        queueManager.addClientToQueue(new Client("tester_new", new Storage()));
        uploadManager.startUploading();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        queueManager.addClientToQueue(new Client("tester1",
                new Storage(Set.of(
                        new FileResource("file1", 120.0),
                        new FileResource("file2", 25.0),
                        new FileResource("file3", 30.0),
                        new FileResource("file4", 54.0),
                        new FileResource("file5", 61.0),
                        new FileResource("file6", 110.0)))));
        queueManager.addClientToQueue(new Client("test2", new Storage(Set.of(
                new FileResource("file11", 320.0),
                new FileResource("file12", 10.0),
                new FileResource("file13", 51.0),
                new FileResource("file14", 81.0)))));


        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            Set<Client> clientSet = queueManager.getClientsQueue();
            queueTable.setItems(FXCollections.observableList(new ArrayList<>(clientSet)));
            queueTable.refresh();

            Set<UploadFileAction> actionSet = uploadManager.getActionSet();
            uploadTable.setItems(FXCollections.observableList(new ArrayList<>(actionSet)));
            uploadTable.refresh();
        }, 0, REFRESH_RATE, TimeUnit.MILLISECONDS);

    }


}
