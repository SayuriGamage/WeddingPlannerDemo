package lk.ijse.backenddemo.service;


import lk.ijse.backenddemo.dto.ServiceDTO;
import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.entity.User;

import java.util.List;
import java.util.Optional;


public interface ServiceService {


    ServiceDTO saveService(ServiceDTO serviceDTO, User user);

    List<Services> getAllServices();

    ServiceDTO getServiceById(String id);

    void deleteCategory(String id);


    List<Services> getServicesByUserId(Optional<User> user);

    List<Services> getServicesByCategory(String category);


    Optional<Services> getAllServicesById(String serviceId);

}
