package com.marcusmoura.server.service.implementation;

import com.marcusmoura.server.enumeration.Status;
import com.marcusmoura.server.model.Server;
import com.marcusmoura.server.repositories.ServerRepository;
import com.marcusmoura.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.marcusmoura.server.enumeration.Status.SERVER_DOWN;
import static com.marcusmoura.server.enumeration.Status.SERVER_UP;
import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor // basically the same as @autowired
@Service
@Transactional
@Slf4j // to have the execution printed on the console to know what's happening.
public class ServerServiceImplementation implements ServerService {


    private final ServerRepository repo;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return repo.save(server);
    }



    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}", ipAddress);
        Server server = repo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        repo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return repo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Pinging server ID: {}", id);
        return repo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return repo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by ID: {}", id);
        repo.deleteById(id);
        return TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/"+imageNames[
                new Random().nextInt(4)]).toUriString();
    }
}
