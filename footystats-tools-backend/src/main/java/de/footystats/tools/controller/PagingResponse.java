package de.footystats.tools.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
public class PagingResponse<ELEMENTTYPE> implements Serializable {
    @Setter
    @Getter
    private int totalPages;
    @Setter
    @Getter
    private long totalElements;
    @Setter
    @Getter
    private Collection<ELEMENTTYPE> elements;
}
