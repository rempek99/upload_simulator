package pl.zimnyciechan.uploadsimulator.service;

import pl.zimnyciechan.uploadsimulator.model.objects.Client;

import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class QueueManager {

    private static QueueManager instance;

    private Set<Client> clientSet = new HashSet<>();

    public static QueueManager getInstance() {
        if (instance == null) {
            instance = new QueueManager();
        }
        return instance;
    }

    public void addClientToQueue(Client client) {
        clientSet.add(client);
        client.setStampAtQueue(Instant.now());
    }

    public Set<Client> getClientsQueue() {
        return new HashSet<>(clientSet);
    }

    public Client getNextClient() {
        Client client = computeNext();
        if (client!=null && client.getStorage().getFilesCollection().size() == 1) {
            clientSet.remove(client);
        }
        return client;
    }

    private Client computeNext() {
        Iterator<Client> iterator = clientSet.iterator();
        if (iterator.hasNext()) {
            //TODO zaimplementować logikę wyciągania kolejnego klineta z kolejki
            return iterator.next();
        }
        return null;
    }
}
