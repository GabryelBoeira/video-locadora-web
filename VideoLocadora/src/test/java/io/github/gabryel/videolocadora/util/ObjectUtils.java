package io.github.gabryel.videolocadora.util;

import io.github.gabryel.videolocadora.model.dto.address.AddressDetailDTO;
import io.github.gabryel.videolocadora.model.dto.address.AddressSaveDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.model.entity.AddressEntity;
import io.github.gabryel.videolocadora.model.entity.CustomerEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ObjectUtils {

    /**
     * Creates and returns a valid CustomerEntity object for testing purposes.
     *
     * @return a CustomerEntity with valid test data
     */
    public CustomerEntity customerEntity() {
        CustomerEntity customer = new CustomerEntity("João Silva", "12345678909", "teste@ig.com.br");
        customer.setId(1L);
        customer.addAddress(addressEntity());
        return customer;
    }

    /**
     * Creates and returns a valid AddressEntity object for testing purposes.
     *
     * @return an AddressEntity with valid test data
     */
    public AddressEntity addressEntity() {
        AddressEntity address = new AddressEntity("Av. Paulista", 1234, "São Paulo", "SP", "Brasil", "01311000", "Bela Vista", "Casa 1");
        address.setId(1L);
        return address;
    }

    /**
     * Creates and returns a list containing a single valid AddressEntity object for testing purposes.
     *
     * @return a list with one AddressEntity containing valid test data
     */
    public List<AddressEntity> listAddressEntity() {
        return Collections.singletonList(addressEntity());
    }

    /**
     * Creates and returns a valid CustomerDetailDTO object for testing purposes.
     *
     * @return a CustomerDetailDTO with valid test data
     */
    public CustomerDetailDTO customerDetailDTO() {
        return new CustomerDetailDTO(1L, "João Silva", "123.456.789-09", false, "teste@ig.com.br", Collections.singletonList(addressDetailDTO()));
    }

    /**
     * Creates and returns a valid AddressDetailDTO object for testing purposes.
     *
     * @return an AddressDetailDTO with valid test data
     */
    public AddressDetailDTO addressDetailDTO() {
        return new AddressDetailDTO(1L, "Av. Paulista", 1234, "São Paulo", "SP", "Brasil", "01311-000", "Bela Vista", "Casa 1", true, true);
    }

    /**
     * Creates and returns a list containing a single valid AddressDetailDTO object for testing purposes.
     *
     * @return a list with one AddressDetailDTO containing valid test data
     */
    public List<AddressDetailDTO> addressDetailDTOList() {
        return Collections.singletonList(addressDetailDTO());
    }

    /**
     * Creates and returns a valid CustomerSaveDTO object for testing purposes.
     *
     * @return a CustomerSaveDTO with valid test data
     */
    public CustomerSaveDTO customerSaveDTO() {
        return new CustomerSaveDTO("João Silva", "123.456.789-09", "teste@ig.com.br", Collections.singletonList(addressSaveDTO()));
    }

    /**
     * Creates and returns a valid AddressSaveDTO object for testing purposes.
     *
     * @return an AddressSaveDTO with valid test data
     */
    public AddressSaveDTO addressSaveDTO() {
        return new AddressSaveDTO("Av. Paulista", "São Paulo", "SP", "Brasil", "01311-000", "Bela Vista", null, 1234);
    }

}
