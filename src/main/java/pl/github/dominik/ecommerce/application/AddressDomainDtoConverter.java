package pl.github.dominik.ecommerce.application;

import lombok.val;
import org.springframework.stereotype.Component;
import pl.github.dominik.ecommerce.domain.Address;

@Component
class AddressDomainDtoConverter {

    public Address convert(Address address) {
        if (address == null) {
            return null;
        }

        return new AddressDto(address.getCountry(), address.getCity(),
                address.getStreet(), address.getZipCode());
    }

    public Address convert(AddressDto address) {
        if (address == null) {
            return null;
        }

        val result = new Address();
        result.setCountry(address.getCountry());
        result.setCity(address.getCity());
        result.setStreet(address.getStreet());
        result.setZipCode(address.getZipCode());
        return result;
    }
}
