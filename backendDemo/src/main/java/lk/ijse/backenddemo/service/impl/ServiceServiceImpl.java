package lk.ijse.backenddemo.service.impl;

import lk.ijse.backenddemo.dto.ServiceDTo;
import lk.ijse.backenddemo.entity.Service;
import lk.ijse.backenddemo.entity.Vendor;
import lk.ijse.backenddemo.repo.ServiceRepository;
import lk.ijse.backenddemo.repo.VendorRepository;
import lk.ijse.backenddemo.service.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ServiceDTo saveService(ServiceDTo serviceDTO) {
        if (serviceDTO.getVendorId() == null) {
            throw new IllegalArgumentException("vendorId is required");
        }
        Service service = modelMapper.map(serviceDTO, Service.class);
        Long vendorId = serviceDTO.getVendorId();
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + vendorId));
        service.setVendor(vendor);
        Service savedService = serviceRepository.save(service);
        ServiceDTo savedDTO = modelMapper.map(savedService, ServiceDTo.class);
        savedDTO.setVendorId(savedService.getVendor().getId());
        return savedDTO;
    }


    @Override
    public ServiceDTo updateService(Long id, ServiceDTo serviceDTO) {
        Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
        existingService.setServiceName(serviceDTO.getServiceName());
        existingService.setDescription(serviceDTO.getDescription());
        existingService.setPrice(serviceDTO.getPrice());
        existingService.setAvailability(serviceDTO.getAvailability());
        if (serviceDTO.getVendorId() != null) {
            Long vendorId = serviceDTO.getVendorId();
            Vendor vendor = vendorRepository.findById(vendorId)
                    .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + vendorId));
            existingService.setVendor(vendor);
        }

        Service updatedService = serviceRepository.save(existingService);
        ServiceDTo updatedDTO = modelMapper.map(updatedService, ServiceDTo.class);
        updatedDTO.setVendorId(updatedService.getVendor().getId());
        return updatedDTO;
    }

    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public List<ServiceDTo> searchServices(String serviceName) {
        List<Service> services = serviceRepository.findByServiceNameContaining(serviceName);
        return services.stream()
                .map(s -> {
                    ServiceDTo dto = modelMapper.map(s, ServiceDTo.class);
                    dto.setVendorId(s.getVendor().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceDTo> getAllServices() {
        List<Service> services = serviceRepository.findAll();
        return services.stream()
                .map(s -> {
                    ServiceDTo dto = modelMapper.map(s, ServiceDTo.class);
                    dto.setVendorId(s.getVendor().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
