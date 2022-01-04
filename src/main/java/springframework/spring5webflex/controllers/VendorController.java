package springframework.spring5webflex.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springframework.spring5webflex.domain.Vendor;
import springframework.spring5webflex.repositories.VendorRepository;

/**
 * @author Silviu_Titirca on 1/4/2022
 */
@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    Flux<Vendor> list(){
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    Mono<Vendor> getById(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream){
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/api/v1/vendors/{id}")
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/api/v1/vendors/{id}")
    Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor){
        Vendor foundVendor = vendorRepository.findById(id).block();

        if(foundVendor.getFirstName() != vendor.getFirstName() || foundVendor.getLastName() != vendor.getLastName()){
            foundVendor.setFirstName(vendor.getFirstName());
            foundVendor.setLastName(vendor.getLastName());
            return vendorRepository.save(foundVendor);
        }
        return Mono.just(foundVendor);
    }

    @DeleteMapping("/api/v1/vendors/{id}")
    Mono<Void> delete(@PathVariable String id){
        return vendorRepository.deleteById(id);
    }
}
