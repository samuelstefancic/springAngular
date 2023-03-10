package work.sam.server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import work.sam.server.enumeration.Status;
import work.sam.server.model.Server;
import work.sam.server.services.ServerService;

import java.io.IOException;
import java.util.function.BooleanSupplier;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServerServiceTest {

    @Autowired
    private ServerService serverService;

    @Test
    void create() {
        Server item = new Server();
        item.setIpAdress("192.1681.1.0");
        item.setName("DOCKER_Server");
        item.setStatus(Status.SERVER_UP);
        serverService.create(item);
    }

    @Test
    void testCreation() {
        assertNotNull(serverService.getServerByName("DOCKER_Server"));
    }


    @Test
    void testUpdateServer() {
        // Créer un serveur
        Server server = new Server();
        server.setIpAdress("192.168.10.1");
        server.setName("Server 1");
        server.setStatus(Status.SERVER_UP);
        Server savedServer = serverService.createServer(server);

        // Mettre à jour le serveur
        savedServer.setName("Server 2");
        Server updatedServer = serverService.updateServer(savedServer.getId(), savedServer);

        // Vérifier que le serveur a bien été mis à jour
        assertNotNull(updatedServer);
        assertEquals(savedServer.getId(), updatedServer.getId());
        assertEquals("Server 2", updatedServer.getName());
        assertEquals("192.168.10.1", updatedServer.getIpAdress());
        assertEquals(Status.SERVER_UP, updatedServer.getStatus());
    }

    @Test
    public void testPing() {
        String ipAdress = "127.0.0.1";
        Server server = new Server();
        server.setName("Server 1");
        server.setIpAdress(ipAdress);
        serverService.create(server);

        Server result = serverService.ping(ipAdress);

        assertEquals(Status.SERVER_UP, result.getStatus());
        assertNotNull(result.getLastPing());
    }
    @Test
    public void testFetchImage() throws IOException {
        Long serverId = 45L; // replace with ID of a server in your database
        byte[] imageBytes = serverService.fetchImage(serverId);
        assertNotNull(imageBytes);
        assertTrue(imageBytes.length > 0);
    }
}
