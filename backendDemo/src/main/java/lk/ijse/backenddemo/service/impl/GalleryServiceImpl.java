package lk.ijse.backenddemo.service.impl;

import lk.ijse.backenddemo.dto.GalleryDTO;
import lk.ijse.backenddemo.dto.ServiceDTO;
import lk.ijse.backenddemo.entity.Gallery;
import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.repo.GalleryRepository;
import lk.ijse.backenddemo.service.GalleryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GalleryServiceImpl implements GalleryService {


    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GalleryDTO saveGallery(GalleryDTO galleryDTO, Services services) {
        Gallery gallery=new Gallery();
        gallery.setServices(services);
        gallery.setImage(galleryDTO.getImage());
        gallery = galleryRepository.save(gallery);
        return modelMapper.map(gallery, GalleryDTO.class);
    }

    @Override
    public List<Gallery> getAllGalerry(Optional<Services> services) {

        List<Gallery> galleries=galleryRepository.findGallery(services.get());
        return  galleries.stream().map(gallery -> {
           Gallery dto=new Gallery();
           dto.setGid(gallery.getGid());
           dto.setImage(gallery.getImage());
           dto.setServices(gallery.getServices());
           return dto;
        }).collect(Collectors.toList());
    }


}
