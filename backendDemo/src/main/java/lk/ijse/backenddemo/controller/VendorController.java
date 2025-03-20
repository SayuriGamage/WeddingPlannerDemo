package lk.ijse.backenddemo.controller;

import lk.ijse.backenddemo.dto.VendorDTO;
import lk.ijse.backenddemo.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/save")
    public ResponseEntity<VendorDTO> saveVendor(@RequestBody VendorDTO vendorDTO) {
        VendorDTO savedVendor = vendorService.saveVendor(vendorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVendor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorDTO> updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        VendorDTO updatedVendor = vendorService.updateVendor(id, vendorDTO);
        return ResponseEntity.ok(updatedVendor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<VendorDTO>> searchVendors(@RequestParam String businessName) {
        List<VendorDTO> vendors = vendorService.searchVendors(businessName);
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<VendorDTO> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }
}
