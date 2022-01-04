package springframework.spring5webflex.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Silviu_Titirca on 1/4/2022
 */
@Data
@Document
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Category {

    @Id
    private String id;

    private String description;
}
