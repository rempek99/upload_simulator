import org.junit.Test;
import pl.zimnyciechan.uploadsimulator.model.objects.Client;
import pl.zimnyciechan.uploadsimulator.model.resources.FileResource;
import pl.zimnyciechan.uploadsimulator.model.resources.Storage;
import pl.zimnyciechan.uploadsimulator.service.QueueManager;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class QueueManagerTests {

    private static Client testClient = new Client("tester1",
            new Storage(Set.of(
                    new FileResource("file1", 120.0),
                    new FileResource("file2", 25.0),
                    new FileResource("file3", 30.0))));
    private static Client testClient2 = new Client("tester2",
            new Storage(Set.of(
                    new FileResource("file11", 320.0),
                    new FileResource("file12", 10.0))));

    @Test
    public void addClientToQueueTest() throws InterruptedException {
        QueueManager manager = QueueManager.getInstance();
        manager.addClientToQueue(testClient);
        Thread.sleep(1000);
        manager.addClientToQueue(testClient2);
        assertTrue(testClient.getStampAtQueue().isBefore(testClient2.getStampAtQueue()));
    }

}
