package pl.zimnyciechan.uploadsimulator.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class MainController {

    @FXML
    private TableView uploadTable;

    @FXML
    public void buttonClick(ActionEvent actionEvent) {

        System.out.println(uploadTable.getItems());
    }
}
