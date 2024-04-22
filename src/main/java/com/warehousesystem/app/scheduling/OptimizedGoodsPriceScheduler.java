package com.warehousesystem.app.scheduling;

import com.warehousesystem.app.handler.exception.EmptyGoodsException;
import com.warehousesystem.app.handler.exception.NotFoundByIdException;
import com.warehousesystem.app.handler.exception.SQLUniqueException;

import java.io.IOException;
import java.sql.SQLException;

public interface OptimizedGoodsPriceScheduler {

    void changeGoodsValue() throws EmptyGoodsException, SQLUniqueException, NotFoundByIdException, SQLException, IOException;

}
