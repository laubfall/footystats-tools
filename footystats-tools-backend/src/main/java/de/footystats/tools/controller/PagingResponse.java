package de.footystats.tools.controller;

import java.io.Serializable;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Basic response type for responses that contain a list of elements and paging information.
 *
 * @param <ELEMENTTYPE> The type of the elements in the list.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponse<ELEMENTTYPE extends Serializable> implements Serializable {

	private int totalPages;
	private long totalElements;
	private Collection<ELEMENTTYPE> elements;
}
