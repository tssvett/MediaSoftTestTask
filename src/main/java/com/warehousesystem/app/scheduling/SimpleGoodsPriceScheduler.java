package com.warehousesystem.app.scheduling;

import com.warehousesystem.app.handler.exception.EmptyProductException;
import com.warehousesystem.app.handler.exception.NotFoundByIdException;
import com.warehousesystem.app.handler.exception.SQLUniqueException;

public interface SimpleGoodsPriceScheduler {

    void changeGoodsValue() throws EmptyProductException, SQLUniqueException, NotFoundByIdException;
}
