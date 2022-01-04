package springframework.spring5webflex.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import springframework.spring5webflex.domain.Category;

/**
 * @author Silviu_Titirca on 1/4/2022
 */

public interface CategoryRepository extends ReactiveMongoRepository <Category, String> {

}
