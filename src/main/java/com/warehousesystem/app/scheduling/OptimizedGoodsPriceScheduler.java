package com.warehousesystem.app.scheduling;

import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;

public interface OptimizedGoodsPriceScheduler {

    void changeGoodsValue() throws EmptyGoodsException, SQLUniqueException;

}
