package lk.ijse.backenddemo.service;

import lk.ijse.backenddemo.dto.VendorDTO;

import java.util.List;

public interface VendorService {
    VendorDTO saveVendor(VendorDTO vendorDTO);
    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);
    void deleteVendor(Long id);
    List<VendorDTO> searchVendors(String businessName);
    List<VendorDTO> getAllVendors();
}
