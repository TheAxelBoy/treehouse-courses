package com.teamtreehouse.vending;

import com.sun.tools.corba.se.idl.constExpr.Not;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alexlazau on 19/11/2016.
 */
public class VendingMachineTest {

    private VendingMachine machine;

    public class NotifierSub implements Notifier {

        @Override
        public void onSale(Item item) {
            return;
        }
    }

    @Before
    public void setUp() throws Exception {
        Notifier notifier = new NotifierSub();
        machine = new VendingMachine(notifier, 10, 10, 10);
        machine.restock("A1", "Twinkies", 1, 30, 75);
    }

    @Test
    public void vendingWhenStockedReturnsItem() throws Exception {
        machine.addMoney(75);

        Item item = machine.vend("A1");

        assertEquals("Twinkies", item.getName());
    }

    @Test
    public void salesTotalIsIncrementedAfterSuccessfulVend() throws Exception {
        machine.addMoney(75);

        machine.vend("A1");

        assertEquals(machine.getRunningSalesTotal(), 75);
    }
}