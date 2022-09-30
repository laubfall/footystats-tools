package de.ludwig.footystats.tools.backend.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paging implements Serializable {
    @Setter
    @Getter
    private int page;
    @Setter
    @Getter
    private int size;
    @Setter
    @Getter
    private Sort.Direction direction;
    @Setter
    @Getter
    private List<String> properties;

    public PageRequest convert(){
        if(direction == null && (properties == null || properties.isEmpty())){
            return PageRequest.of(page, size);
        }

        if(direction != null){
            return PageRequest.of(page, size, Sort.by(direction, "country"));
        }

        /*
        if(sort != null){
            return PageRequest.of(page, size, sort);
        }

         */

        if(direction != null && properties != null){
            return PageRequest.of(page, size, direction, properties.toArray(new String[]{}));
        }

        return null;
    }
}
