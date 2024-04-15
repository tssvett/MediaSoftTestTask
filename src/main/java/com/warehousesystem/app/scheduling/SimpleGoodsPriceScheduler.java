package com.warehousesystem.app.scheduling;

import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;

public interface SimpleGoodsPriceScheduler {

    void changeGoodsValue() throws EmptyGoodsException, SQLUniqueException;
}
