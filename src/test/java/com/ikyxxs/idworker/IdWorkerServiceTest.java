package com.ikyxxs.idworker;

import com.ikyxxs.idworker.enums.IdWorkerType;
import com.ikyxxs.idworker.service.IdWorkerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IdWorkerServiceTest {

    @Autowired
    private IdWorkerService idWorkerService;

    @Test
    public void testGetNextID() {
        System.out.println(idWorkerService.getNextID(IdWorkerType.ORDER_ID));
        System.out.println(idWorkerService.getNextID(IdWorkerType.ORDER_ID));
    }
}
