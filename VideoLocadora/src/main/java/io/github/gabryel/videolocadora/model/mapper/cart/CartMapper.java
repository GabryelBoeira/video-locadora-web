package io.github.gabryel.videolocadora.model.mapper.cart;

import io.github.gabryel.videolocadora.model.dto.cart.CartDetailDTO;
import io.github.gabryel.videolocadora.model.entity.cart.CartEntity;
import io.github.gabryel.videolocadora.model.entity.cart.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.name")
    @Mapping(target = "itemCount", expression = "java(cart.getItems().size())")
    @Mapping(target = "expired", expression = "java(cart.isExpired())")
    CartDetailDTO toDetailDTO(CartEntity cart);

    @Mapping(target = "movieId", source = "movie.id")
    @Mapping(target = "movieTitle", source = "movie.title")
    @Mapping(target = "reservationExpired", expression = "java(item.isReservationExpired())")
    CartDetailDTO.CartItemDetailDTO toItemDetailDTO(CartItemEntity item);
}