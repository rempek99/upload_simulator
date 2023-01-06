package pl.zimnyciechan.uploadsimulator.service;

import lombok.Getter;
import lombok.Setter;
import pl.zimnyciechan.uploadsimulator.model.objects.AbstractDevice;
import pl.zimnyciechan.uploadsimulator.model.objects.Client;
import pl.zimnyciechan.uploadsimulator.model.objects.Drive;
import pl.zimnyciechan.uploadsimulator.model.resources.FileResource;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class UploadFileAction extends Thread {

    private static final long TIME_TICK = 500;
    private static final Double SIZE_PER_TICK = 1.0;
    @Getter
    private final Client sender;
    @Getter
    private final Drive reciever;

    @Getter
    private String fileName;

    @Getter
    private Double fullFileSize;

    @Getter
    private Double uploadedFileSize = 0.0;

    private String info;

    public UploadFileAction(String name, Client sender, Drive reciever) {
        super(name);
        this.sender = sender;
        this.reciever = reciever;
    }


    @Override
    public void run() {
//        reciever.setBusy(true);
        final FileResource fileToSend = sender.getStorage().getFirstFile();
        sender.getStorage().deleteFile(fileToSend);
        fileName = fileToSend.getName();
        fullFileSize = fileToSend.getSize();
        synchronized (reciever) {
            info = String.format("[%s] Sending file: '%s' from: %s  to: %s ...%n", getName(), fileToSend.getName(), sender.getName(), reciever.getName());
            printInfo();
            while (uploadedFileSize < fullFileSize) {
                try {
                    Thread.sleep(TIME_TICK);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                uploadedFileSize += SIZE_PER_TICK;
                info = String.format("[%s] Sended %s / %s ...%n", getName(), uploadedFileSize, fileToSend.getSize());
            }
            reciever.getStorage().upload(fileToSend);
            sender.getStorage().deleteFile(fileToSend);
        }
//        reciever.setBusy(false);
        info = String.format("[%s] Sending finished.%n", getName());
    }

    public void printInfo() {
        if (info != null) {
            System.out.println(info);
        }
    }

    public String getRecieverName() {
        return reciever.getName();
    }

    public String getSenderName() {
        return sender.getName();
    }

    public String getUploaded() {
        return String.format("%s / %s", uploadedFileSize, fullFileSize);
    }
}
