package com.sample.retailstore.request;

import com.sample.retailstore.domain.ItemObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author irfan.nagoo
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemRequest {

    @NotNull
    @Valid
    private ItemObject item;

}
