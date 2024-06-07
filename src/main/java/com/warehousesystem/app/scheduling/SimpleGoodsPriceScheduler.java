package com.warehousesystem.app.scheduling;

import com.warehousesystem.app.handler.exception.EmptyGoodsException;
import com.warehousesystem.app.handler.exception.NotFoundByIdException;
import com.warehousesystem.app.handler.exception.SQLUniqueException;

public interface SimpleGoodsPriceScheduler {

    void changeGoodsValue() throws EmptyGoodsException, SQLUniqueException, NotFoundByIdException;
}
