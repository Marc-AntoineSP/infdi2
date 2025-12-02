package fr.awu.annuaire.service;

import java.util.List;

import fr.awu.annuaire.model.Service;

import fr.awu.annuaire.repository.ServiceRepository;

public class ServiceService {
    private ServiceRepository serviceRepository;

    public ServiceService() {
        this.serviceRepository = new ServiceRepository();
    }

    public void save(Service service) throws IllegalArgumentException {
        if(service.getName() == null || service.getName().isBlank()) {
            throw new IllegalArgumentException("CreationError: Service name cannot be null");
        }
        serviceRepository.save(service);
    }

    public List<Service> getAll() {
        return serviceRepository.getAll();
    }

    public void update(Service service) throws IllegalArgumentException {
        if(service.getName() == null || service.getName().isBlank()) {
            throw new IllegalArgumentException("CreationError: Service name cannot be null");
        }
        serviceRepository.update(service);
    }

    public void delete (Service service) {
        serviceRepository.delete(service);
    }
}
