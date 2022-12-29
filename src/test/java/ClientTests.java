import org.junit.Test;
import pl.zimnyciechan.uploadsimulator.model.objects.Client;
import pl.zimnyciechan.uploadsimulator.model.resources.FileResource;
import pl.zimnyciechan.uploadsimulator.model.resources.Storage;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ClientTests {

    private static Client testClient = new Client("tester1",
            new Storage(Set.of(
                    new FileResource("file1", 120.0),
                    new FileResource("file2", 25.0),
                    new FileResource("file3", 30.0))));

    @Test
    public void clientHasSortedFilesTest() {
        final String[] expectedOrder = {"file2", "file3", "file1"};
        int i = 0;
        for (FileResource fileResource : testClient.getStorage().getFilesCollection()) {
            assertEquals(expectedOrder[i], fileResource.getName());
            i++;
        }
        assertEquals(3, i);
    }


}
