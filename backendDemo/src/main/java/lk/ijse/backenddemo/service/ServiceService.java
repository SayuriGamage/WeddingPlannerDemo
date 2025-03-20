package lk.ijse.backenddemo.service;
import lk.ijse.backenddemo.dto.ServiceDTo;

import java.util.List;

public interface ServiceService {
    ServiceDTo saveService(ServiceDTo serviceDTO);
    ServiceDTo updateService(Long id, ServiceDTo serviceDTO);
    void deleteService(Long id);
    List<ServiceDTo> searchServices(String serviceName);
    List<ServiceDTo> getAllServices();
}
