package com.warehousesystem.app.scheduling.impl;

import com.warehousesystem.app.annotation.MeasureWorkingTime;
import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.repository.WarehouseGoodRepository;
import com.warehousesystem.app.scheduling.OptimizedGoodsPriceScheduler;
import com.warehousesystem.app.scheduling.conditions.SchedulingAndOptimizationCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Conditional(SchedulingAndOptimizationCondition.class)
@EnableScheduling
public class OptimizedGoodsPriceSchedulerImpl implements OptimizedGoodsPriceScheduler {

    @Value("${app.scheduling.priceIncreasePercentage}")
    private int percentage;
    @Autowired
    private WarehouseGoodRepository warehouseGoodRepository;
    @Autowired
    private DataSource dataSource;


    @Transactional
    @MeasureWorkingTime
    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    public void changeGoodsValue() throws NotFoundByIdException, SQLUniqueException, EmptyGoodsException, RuntimeException, SQLException, IOException {

        Connection connection = dataSource.getConnection();

        // Получение всех записей из таблицы warehouse_goods
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM warehouse_goods");
        ResultSet resultSet = statement.executeQuery();
        BufferedWriter writer = new BufferedWriter(new FileWriter("goods.txt"));
        // Список для хранения обновленных объектов
        List<WarehouseGood> updatedGoods = new ArrayList<>();

        int count = 0;
        int BATCH_SIZE = 100000;
        while (resultSet.next()) {
            // Увеличение цены товара на 10%
            double newPrice = resultSet.getDouble("price") * (100 + percentage) / 100;
            WarehouseGood updatedGood = WarehouseGood.builder()
                    .id(UUID.fromString(resultSet.getObject("id").toString()))
                    .name(resultSet.getString("name"))
                    .article(resultSet.getString("article"))
                    .description(resultSet.getString("description"))
                    .category(resultSet.getString("category"))
                    .price(newPrice)
                    .quantity(resultSet.getInt("quantity"))
                    .lastUpdateTime(LocalDateTime.parse(resultSet.getString("last_update"), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                    .creationTime(LocalDateTime.parse(resultSet.getString("creation_time"), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                    .build();
            // Сохранение обновленного объекта
            updatedGoods.add(updatedGood);

            // Выполнение пакетного обновления, когда список обновленных объектов достигнет размера порции
            if (++count % BATCH_SIZE == 0) {
                updateGoods(updatedGoods, connection, writer);
                // Очистка списка обновленных объектов
                updatedGoods.clear();
                writer.flush();
                System.out.println("Updated " + count + " rows");
            }
        }

        // Выполнение пакетного обновления для оставшихся обновленных объектов
        if (!updatedGoods.isEmpty()) {
            updateGoods(updatedGoods, connection, writer);
        }

        // Закрытие ресурсов
        resultSet.close();
        statement.close();
        connection.close();

    }

    private static void updateGoods(List<WarehouseGood> goods, Connection connection, BufferedWriter writer) throws SQLException, IOException {
        String query = "UPDATE warehouse_goods SET price = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (WarehouseGood good : goods) {
            preparedStatement.setDouble(1, good.getPrice());
            preparedStatement.setObject(2, good.getId());
            preparedStatement.addBatch();
            writer.write(good.toString());
            writer.newLine();
        }
        preparedStatement.executeBatch();
        preparedStatement.close();
    }




}
