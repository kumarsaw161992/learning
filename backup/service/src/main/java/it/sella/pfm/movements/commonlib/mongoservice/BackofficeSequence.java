package it.sella.pfm.movements.commonlib.mongoservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "app_sequence")
@TypeAlias("sequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackofficeSequence {

    @Id
    private String name;
    private long value;
}
