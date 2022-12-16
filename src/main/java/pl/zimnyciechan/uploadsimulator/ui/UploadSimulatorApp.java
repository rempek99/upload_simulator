package pl.zimnyciechan.uploadsimulator.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.zimnyciechan.uploadsimulator.ui.models.ClientModel;

import java.util.ArrayList;
import java.util.List;

public class UploadSimulatorApp extends Application {

    private static final double DISK_RECTANGLE_WIDTH = 100.0;
    private static final double CLIENT_RECTANGLE_HEIGHT = 60.0;
    private double rectangleOffsetX = 10.0;

    private int clientCount = 0;
    private HBox sendingBox, queueBox;

    private boolean simulationRunning = false;


    public static void main(String[] args) throws InterruptedException {

//        System.out.println("Hello world!");
//
//        final Client c1 = new Client("tester1", new Storage(List.of(testFiles[0], testFiles[1])));
//        final Client c2 = new Client("tester2", new Storage(List.of(testFiles[2], testFiles[3])));
//        final Client c3 = new Client("tester3", new Storage(List.of(testFiles[2], testFiles[3], testFiles[4])));
//
//        final Drive drive = new Drive("myDrive");
//
//
//        UploadFileAction testAction = new UploadFileAction("Action1", c3, drive);
//        UploadFileAction testAction2 = new UploadFileAction("Action2", c2, drive);
//        UploadFileAction testAction3 = new UploadFileAction("Action3", c1, drive);
//        Thread testThread = new Thread(testAction);
//        Thread testThread2 = new Thread(testAction2);
//        Thread testThread3 = new Thread(testAction3);
//        testThread.start();
//        testThread2.start();
//        testThread3.start();
//        testThread.join();
//        testThread2.join();
//        testThread3.join();
//        System.out.println(drive);
        System.out.println("Starting app...");
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        GridPane grid = new GridPane();
        grid.setLayoutX(15);
        grid.setHgap(10);
        grid.setVgap(12);

        List<Node> diskRects = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            diskRects.add(generateRectangle(String.format("Disk #%s", i), Color.RED, DISK_RECTANGLE_WIDTH));
        }

        List<Node> sendingClientsRects = new ArrayList<>();
        for (; clientCount < 5; clientCount++) {
            sendingClientsRects.add(new ClientModel("Client " + clientCount, Color.YELLOW));
//            sendingClientsRects.add(generateRectangle(String.format("Client #%s", clientCount), Color.YELLOW, CLIENT_RECTANGLE_HEIGHT));
        }

        List<Node> clientsQueueRects = new ArrayList<>();
        for (; clientCount < 15; clientCount++) {
            clientsQueueRects.add(new ClientModel("Client " + clientCount, Color.CYAN));
//            clientsQueueRects.add(generateRectangle(String.format("Client #%s", clientCount), Color.CYAN, CLIENT_RECTANGLE_HEIGHT));
        }

        HBox disksBox = new HBox();
        disksBox.getChildren().addAll(diskRects);
        disksBox.setSpacing(10.0);
        sendingBox = new HBox();
        sendingBox.getChildren().addAll(sendingClientsRects);
        sendingBox.setSpacing(5);

        queueBox = new HBox();
        queueBox.getChildren().addAll(clientsQueueRects);
        queueBox.setSpacing(5.0);

        Button stepButton = new Button();
        stepButton.setText("Step");

        stepButton.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                processStep();
            }
        });

        Button addClientButton = new Button();
        addClientButton.setText("Add Client");

        addClientButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            queueBox.getChildren().add(new ClientModel("Client " + clientCount, Color.CYAN));
            clientCount++;
        });


        grid.add(disksBox, 0, 0);
        grid.add(new Text("Przesyłający"), 0, 9);
        grid.add(sendingBox, 0, 10);
        grid.add(new Text("Kolejka"), 2, 9);
        grid.add(queueBox, 2, 10);
        grid.add(stepButton, 2, 11);
        grid.add(addClientButton, 2, 12);


        Button startButton = new Button("Start");
        startButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            startSimulation();
        });
        grid.add(startButton, 2, 13);

        Button stopButton = new Button("Stop");
        stopButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            stopSimulation();
        });
        grid.add(stopButton, 2, 14);


        Scene scene = new Scene(grid, 1500, 800);

        stage.setTitle("Upload Simulator");
        stage.setScene(scene);
        stage.show();
    }

    private void processStep() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (sendingBox.getChildren().size() > 0) {
                    sendingBox.getChildren().remove(0);
                }
                while (sendingBox.getChildren().size() < 5 && queueBox.getChildren().size() > 0) {
                    var nextClient = queueBox.getChildren().get(0);
                    if (nextClient instanceof ClientModel) {
                        ((ClientModel) nextClient).setColor(Color.YELLOW);
                        sendingBox.getChildren().add(nextClient);
                        queueBox.getChildren().remove(nextClient);
                    }
                }
            }
        });
    }

    private Node generateRectangle(String textString, Paint fill, double rectSize) {
        Rectangle rect = new Rectangle(rectSize, rectSize);
        rect.setArcWidth(20.0);
        rect.setArcHeight(20.0);
        rect.setFill(fill);
        rect.setStrokeWidth(2.0);
        rect.setStroke(Color.GRAY);
        Text text = new Text(textString);
        return new StackPane(rect, text);
    }

    private void startSimulation() {
        if (simulationRunning) {
            return;
        }
        simulationRunning = true;
        Thread thread = new Thread(new SimulationProcess());
        thread.start();
    }

    private void stopSimulation() {
        simulationRunning = false;
    }

    class SimulationProcess implements Runnable {
        @Override
        public void run() {
            while (simulationRunning) {
                if(sendingBox.getChildren().size()==0){
                    stopSimulation();
                    return;
                }
                ClientModel tester = (ClientModel) sendingBox.getChildren().get(0);
                tester.setUploaded(tester.getUploaded() + 20);
                if (tester.isFinished()) {
                    processStep();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
