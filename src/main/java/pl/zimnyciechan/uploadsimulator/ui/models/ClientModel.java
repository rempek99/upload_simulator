package pl.zimnyciechan.uploadsimulator.ui.models;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
public class ClientModel extends VBox {
    private static final String STATUS_ID = "status.id";
    private static final double CLIENT_RECTANGLE_SIZE = 60.0;
    final private String name;
    final private Double size;
    private Double uploaded = 0.0;
    private Color color;

    private Rectangle clientBody;
    private Text statusText;

    public ClientModel(String name, Color color) {
        super();
        this.name = name;
        this.size = randSize();
        this.color = color;
        init();
    }

    private static Double randSize() {
//        return (double) ThreadLocalRandom.current().nextInt(1, 21) * 100;
        return (double) ThreadLocalRandom.current().nextInt(1, 5) * 100;
    }

    private void init() {
        // Client Icon
        clientBody = new Rectangle(CLIENT_RECTANGLE_SIZE, CLIENT_RECTANGLE_SIZE);
        clientBody.setArcWidth(20.0);
        clientBody.setArcHeight(20.0);
        clientBody.setStrokeWidth(2.0);
        clientBody.setStroke(Color.GRAY);

        // Below status texts
        statusText = new Text();
        statusText.setFont(Font.font("System Regular", FontWeight.LIGHT, 10));

        getChildren().add(clientBody);
        getChildren().add(new Text(getName()));
        getChildren().add(statusText);

        setColor(color);
        setUploaded(uploaded);
    }

    public void setColor(Color value) {
        this.color = value;
        clientBody.setFill(color);
    }

    public void setUploaded(double value) {
        this.uploaded = value;
        String statusString = String.format("%s/%s", getUploaded(), getSize());
        statusText.setText(statusString);
    }

    public boolean isFinished() {
        return uploaded >= size;
    }
}
