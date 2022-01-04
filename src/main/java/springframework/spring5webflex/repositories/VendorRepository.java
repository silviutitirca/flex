package springframework.spring5webflex.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import springframework.spring5webflex.domain.Vendor;

/**
 * @author Silviu_Titirca on 1/4/2022
 */
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
