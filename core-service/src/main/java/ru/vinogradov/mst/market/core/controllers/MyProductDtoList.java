package ru.vinogradov.mst.market.core.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MyProductDtoList {
    List<ProductMyDto> productMyDtos;
}
