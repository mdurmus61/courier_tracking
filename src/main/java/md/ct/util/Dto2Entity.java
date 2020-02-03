package md.ct.util;

import md.ct.domain.Store;
import md.ct.dto.StoreDTO;
import org.springframework.stereotype.Component;

@Component
public class Dto2Entity {

    public Store fromStoreDTO(StoreDTO dto) {
        Store store =  new Store();

        store.setName(dto.getName());
        store.setLatitude(dto.getLat());
        store.setLongitude(dto.getLng());

        return store;
    }
}
