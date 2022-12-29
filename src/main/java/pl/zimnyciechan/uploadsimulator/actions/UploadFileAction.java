package pl.zimnyciechan.uploadsimulator.actions;

import pl.zimnyciechan.uploadsimulator.model.objects.AbstractDevice;
import pl.zimnyciechan.uploadsimulator.model.resources.FileResource;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class UploadFileAction implements Runnable {

    private static final long TIME_TICK = 500;
    private static final Double SIZE_PER_TICK = 10.0;
    private final AbstractDevice sender, reciever;
    private final String name;

    public UploadFileAction(String name, AbstractDevice sender, AbstractDevice reciever) {
        this.name = name;
        this.sender = sender;
        this.reciever = reciever;
    }


    @Override
    public void run() {
        final Set<FileResource> filesToSend = sender.getStorage().getFilesCollection();
        AtomicReference<Double> summaryFilesSize = new AtomicReference<>(0.0);
        filesToSend.forEach(f -> {
            summaryFilesSize.updateAndGet(v -> v + f.getSize());
        });
        double sended = 0.0;
        synchronized (reciever) {
            System.out.printf("[%s] Sending from: %s  to: %s ...%n", name, sender.getName(),
                    reciever.getName());
            while (sended < summaryFilesSize.get()) {
                try {
                    Thread.sleep(TIME_TICK);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sended += SIZE_PER_TICK;
                System.out.printf("[%s] Sended %s / %s ...%n",name, sended, summaryFilesSize);
            }
            reciever.getStorage().upload(filesToSend);
        }
        System.out.printf("[%s] Sending finished.%n", name);
    }
}
