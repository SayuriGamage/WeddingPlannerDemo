package lk.ijse.backenddemo.service.impl;


import lk.ijse.backenddemo.dto.ServiceDTO;
import lk.ijse.backenddemo.entity.Category;
import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.entity.User;
import lk.ijse.backenddemo.repo.ServiceRepository;
import lk.ijse.backenddemo.service.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public ServiceDTO saveService(ServiceDTO serviceDTO, User user ) {
        Services services = new Services();
        services.setTitle(serviceDTO.getTitle());
        services.setDescription(serviceDTO.getDescription());
        services.setCategory(serviceDTO.getCategory());
        services.setTagline(serviceDTO.getTagline());
        services.setBasePrice(serviceDTO.getBasePrice());
        services.setAddress(serviceDTO.getAddress());
        services.setMapAddress(serviceDTO.getMapAddress());
        services.setEmail(serviceDTO.getEmail());
        services.setWebsite(serviceDTO.getWebsite());
        services.setPhone(serviceDTO.getPhone());
        services.setFacebook(serviceDTO.getFacebook());
        services.setTwitter(serviceDTO.getTwitter());
        services.setGoogle(serviceDTO.getGoogle());
        services.setInstagram(serviceDTO.getInstagram());
        services.setImage(serviceDTO.getImage());
        services.setLogo(serviceDTO.getLogo());
        services.setUser(user);
        services = serviceRepository.save(services);
        return modelMapper.map(services, ServiceDTO.class);
    }

    @Override
        public List<Services> getAllServices() {
            return serviceRepository.findAll().stream().map(services -> {
                Services dto = new Services();
                dto.setId(services.getId());
                dto.setCategory(services.getCategory());
                dto.setTitle(services.getTitle());
                dto.setDescription(services.getDescription());
                dto.setTagline(services.getTagline());
                dto.setAddress(services.getAddress());
                dto.setMapAddress(services.getMapAddress());
                dto.setBasePrice(services.getBasePrice());
                dto.setEmail(services.getEmail());
                dto.setWebsite(services.getWebsite());
                dto.setPhone(services.getPhone());
                dto.setFacebook(services.getFacebook());
                dto.setTwitter(services.getTwitter());
                dto.setGoogle(services.getGoogle());
                dto.setInstagram(services.getInstagram());
                dto.setImage(services.getImage());
                dto.setLogo(services.getLogo());
                return dto;
            }).collect(Collectors.toList());
        }

    @Override
    public ServiceDTO getServiceById(String id) {
        Services service = serviceRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Service not found with ID: " + id));

        ServiceDTO dto = new ServiceDTO();
        dto.setUserId(service.getUser().getUid().toString());
        dto.setCategory(service.getCategory());
        dto.setTitle(service.getTitle());
        dto.setDescription(service.getDescription());
        dto.setTagline(service.getTagline());
        dto.setAddress(service.getAddress());
        dto.setMapAddress(service.getMapAddress());
        dto.setBasePrice(service.getBasePrice());
        dto.setEmail(service.getEmail());
        dto.setWebsite(service.getWebsite());
        dto.setPhone(service.getPhone());
        dto.setFacebook(service.getFacebook());
        dto.setTwitter(service.getTwitter());
        dto.setGoogle(service.getGoogle());
        dto.setInstagram(service.getInstagram());
        dto.setImage(service.getImage());
        dto.setLogo(service.getLogo());

        return dto;
    }

    @Override
    public void deleteCategory(String id) {
        if (serviceRepository.existsById(UUID.fromString(id))) {
            serviceRepository.deleteById(UUID.fromString(id));
        }
    }

    @Override
    public List<Services> getServicesByUserId(Optional<User> user) {
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        List<Services> services = serviceRepository.findByUser(user.get());

        return services.stream().map(service -> {
            Services dto = new Services();
            dto.setId(service.getId());
            dto.setCategory(service.getCategory());
            dto.setTitle(service.getTitle());
            dto.setDescription(service.getDescription());
            dto.setTagline(service.getTagline());
            dto.setAddress(service.getAddress());
            dto.setMapAddress(service.getMapAddress());
            dto.setBasePrice(service.getBasePrice());
            dto.setEmail(service.getEmail());
            dto.setWebsite(service.getWebsite());
            dto.setPhone(service.getPhone());
            dto.setFacebook(service.getFacebook());
            dto.setTwitter(service.getTwitter());
            dto.setGoogle(service.getGoogle());
            dto.setInstagram(service.getInstagram());
            dto.setImage(service.getImage());
            dto.setLogo(service.getLogo());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Services> getServicesByCategory(String category) {
        List<Services> services = serviceRepository.findByCategory(category);

        return services.stream().map(service -> {
            Services dto = new Services();
            dto.setId(service.getId());
            dto.setCategory(service.getCategory());
            dto.setTitle(service.getTitle());
            dto.setDescription(service.getDescription());
            dto.setTagline(service.getTagline());
            dto.setAddress(service.getAddress());
            dto.setMapAddress(service.getMapAddress());
            dto.setBasePrice(service.getBasePrice());
            dto.setEmail(service.getEmail());
            dto.setWebsite(service.getWebsite());
            dto.setPhone(service.getPhone());
            dto.setFacebook(service.getFacebook());
            dto.setTwitter(service.getTwitter());
            dto.setGoogle(service.getGoogle());
            dto.setInstagram(service.getInstagram());
            dto.setImage(service.getImage());
            dto.setLogo(service.getLogo());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Services> getAllServicesById(String serviceId) {
        Optional<Services> services = serviceRepository.findServiceByServiceId(UUID.fromString(serviceId));
        if (services.isPresent()) {
            return Optional.of(services.get());
        } else {
            throw new RuntimeException("Service not found with ID: " + serviceId);
        }
    }

    @Override
    public List<Services> getAllServicess() {
        return serviceRepository.findAll().stream().map(services -> {
          Services dto=new Services();
         dto.setId(services.getId());
         dto.setUser(services.getUser());
         dto.setCategory(services.getCategory());
         dto.setTitle(services.getTitle());
         dto.setDescription(services.getDescription());
            dto.setTagline(services.getTagline());
            dto.setAddress(services.getAddress());
            dto.setMapAddress(services.getMapAddress());
            dto.setBasePrice(services.getBasePrice());
            dto.setEmail(services.getEmail());
            dto.setWebsite(services.getWebsite());
            dto.setPhone(services.getPhone());
            dto.setFacebook(services.getFacebook());
            dto.setTwitter(services.getTwitter());
            dto.setGoogle(services.getGoogle());
            dto.setInstagram(services.getInstagram());
            dto.setImage(services.getImage());
            dto.setLogo(services.getLogo());
            return dto;
        }).collect(Collectors.toList());
    }


}







