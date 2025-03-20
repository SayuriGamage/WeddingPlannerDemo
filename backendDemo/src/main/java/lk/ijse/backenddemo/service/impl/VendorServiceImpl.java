package lk.ijse.backenddemo.service.impl;

import lk.ijse.backenddemo.dto.VendorDTO;
import lk.ijse.backenddemo.entity.Vendor;
import lk.ijse.backenddemo.repo.VendorRepository;
import lk.ijse.backenddemo.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VendorDTO saveVendor(VendorDTO vendorDTO) {
        // Convert DTO to entity
        Vendor vendor = modelMapper.map(vendorDTO, Vendor.class);
        Vendor savedVendor = vendorRepository.save(vendor);
        // Convert saved entity back to DTO
        return modelMapper.map(savedVendor, VendorDTO.class);
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id));
        // Update vendor with new values from DTO
        vendor.setBusinessName(vendorDTO.getBusinessName());
        vendor.setBusinessDescription(vendorDTO.getBusinessDescription());
        vendor.setCategory(vendorDTO.getCategory());
        vendor.setLocation(vendorDTO.getLocation());
        vendor.setAvailability(vendorDTO.getAvailability());
        vendor.setCoverPhoto(vendorDTO.getCoverPhoto());
        vendor.setRating(vendorDTO.getRating());
        vendor.setStatus(vendorDTO.getStatus());

        Vendor updatedVendor = vendorRepository.save(vendor);
        return modelMapper.map(updatedVendor, VendorDTO.class);
    }

    @Override
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public List<VendorDTO> searchVendors(String businessName) {
        List<Vendor> vendors = vendorRepository.findByBusinessNameContaining(businessName);
        return vendors.stream()
                .map(vendor -> modelMapper.map(vendor, VendorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        return vendors.stream()
                .map(vendor -> modelMapper.map(vendor, VendorDTO.class))
                .collect(Collectors.toList());
    }
}
