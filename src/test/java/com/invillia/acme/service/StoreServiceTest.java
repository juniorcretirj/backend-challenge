package com.invillia.acme.service;

import com.invillia.acme.domain.inputs.CreateStore;
import com.invillia.acme.domain.inputs.UpdateStore;
import com.invillia.acme.domain.returned.StoreReturn;
import com.invillia.acme.services.StoreService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreServiceTest {

    @Autowired
    private StoreService storeService;

    private UUID id;

    private final String NAME = "Walmart";
    private final String ADDRESS = "FL Miani 12313";

    @Before
    public void setup() {
        CreateStore command = new CreateStore();
        command.setName(NAME);
        command.setAddress(ADDRESS);

        id = storeService.create(command);
    }

    @Test
    public void testFindById() {
        StoreReturn store = storeService.find(id);

        Assert.assertEquals(store.getName(), NAME);
        Assert.assertEquals(store.getAddress(), ADDRESS);
    }

    @Test
    public void testListInvalidName() {
        List<StoreReturn> stores = storeService.list(UUID.randomUUID().toString(), null);

        Assert.assertTrue(stores.size() == 0);
    }

    @Test
    public void testListInvalidAddress() {
        List<StoreReturn> stores = storeService.list(null, UUID.randomUUID().toString());

        Assert.assertTrue(stores.size() == 0);
    }

    @Test
    public void testListInvalidNameAndAddress() {
        List<StoreReturn> stores = storeService.list(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        Assert.assertTrue(stores.size() == 0);
    }

    @Test
    public void testList() {
        List<StoreReturn> stores = storeService.list(null, null);

        Assert.assertTrue(stores.size() == 0);
    }

    @Test
    public void testListByName() {
        List<StoreReturn> stores = storeService.list(NAME, null);

        Assert.assertTrue(stores.size() > 0);
    }

    @Test
    public void testListByAddress() {
        List<StoreReturn> stores = storeService.list(null, ADDRESS);

        Assert.assertTrue(stores.size() > 0);
    }

    @Test
    public void testListByNameAndAddress() {
        List<StoreReturn> stores = storeService.list(NAME, ADDRESS);

        Assert.assertTrue(stores.size() > 0);
    }

    @Test
    public void testUpdateCommand() {
        final String LAST_NAME = "HOME Star";
        final String LAST_ADDRESS = "1514 FL, Miami United States";

        UpdateStore insert = new UpdateStore();
        insert.setId(id);
        insert.setName(LAST_NAME);
        insert.setAddress(LAST_ADDRESS);

        storeService.update(insert);

        StoreReturn query = storeService.find(id);

        Assert.assertEquals(query.getName(), LAST_NAME);
        Assert.assertEquals(query.getAddress(), LAST_ADDRESS);
    }
}
