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
        if (client != null && client.getStorage().getFilesCollection().size() == 1) {
            clientSet.remove(client);
        }
        return client;
    }

    private Client computeNext() {
        final int queueSize = clientSet.size();
//        final long maxTime = getMaxTime(clientSet);
        Client winningClient = null;
        double maxPrio = 0.0;
        System.out.println("CLIENTS LEFT: " + queueSize);
        for (Client client : clientSet) {
            long time = client.getDuration();
            Double size = client.getStorage().getFirstFile().getSize();
            double prio = 0;
//TODO proba #2
            prio = ((Math.pow(queueSize, 2)) * time) / size;

//TODO proba #1
//            if (client.getDuration() <= maxTime / size) {
//                prio = queueSize / size;
////                prio = time * queueSize / size;
//                System.out.println("#1 group");
//            } else {
////                prio = Math.pow(queueSize, time) * size;
//                prio = (double) time / queueSize;
//                System.out.println("#2 group");
//            }
            if (prio > maxPrio) {
                maxPrio = prio;
                winningClient = client;
            }
            System.out.println(client.getName() + " Prio: " + prio);
        }
        if (winningClient != null) {
            System.out.println("Winner: " + winningClient.getName());
            System.out.println("---------------------");
        }
        return winningClient;
    }

    private long getMaxTime(Set<Client> clientSet) {
        long max = 0;
        for (Client client : clientSet) {
            if (client.getDuration() > max) {
                max = client.getDuration();
            }
        }
        return max;
    }
}
