package com.warehousesystem.app.scheduling;

import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;

import java.io.IOException;
import java.sql.SQLException;

public interface OptimizedGoodsPriceScheduler {

    void changeGoodsValue() throws EmptyGoodsException, SQLUniqueException, NotFoundByIdException, SQLException, IOException;

}
