package lk.ijse.backenddemo.controller;

import lk.ijse.backenddemo.dto.ServiceDTo;
import lk.ijse.backenddemo.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping("/save")
    public ResponseEntity<ServiceDTo> saveService(@RequestBody ServiceDTo serviceDTO) {
        ServiceDTo savedService = serviceService.saveService(serviceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTo> updateService(@PathVariable Long id, @RequestBody ServiceDTo serviceDTO) {
        ServiceDTo updatedService = serviceService.updateService(id, serviceDTO);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ServiceDTo>> searchServices(@RequestParam String serviceName) {
        List<ServiceDTo> services = serviceService.searchServices(serviceName);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ServiceDTo>> getAllServices() {
        List<ServiceDTo> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }
}
