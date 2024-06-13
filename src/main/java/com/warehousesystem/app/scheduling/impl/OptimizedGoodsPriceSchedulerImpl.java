package com.warehousesystem.app.scheduling.impl;

import com.warehousesystem.app.annotation.MeasureWorkingTime;
import com.warehousesystem.app.scheduling.OptimizedGoodsPriceScheduler;
import com.warehousesystem.app.scheduling.conditions.SchedulingAndOptimizationCondition;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Conditional(SchedulingAndOptimizationCondition.class)
@EnableScheduling
public class OptimizedGoodsPriceSchedulerImpl implements OptimizedGoodsPriceScheduler {

    @Value("${app.scheduling.priceIncreasePercentage}")
    private int percentage;
    private final DataSource dataSource;
    private final int BATCH_SIZE = 100000;

    public OptimizedGoodsPriceSchedulerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    @MeasureWorkingTime
    @Scheduled(fixedDelayString = "${app.scheduling.period}")
    public void changeGoodsValue() throws RuntimeException, SQLException, IOException {
        try (Connection connection = dataSource.getConnection(); BufferedWriter writer = new BufferedWriter(new FileWriter("goods.txt"))) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                String query = "SELECT * FROM warehouse_goods FOR UPDATE";
                ResultSet resultSet = statement.executeQuery(query);
                String updateQuery = "UPDATE warehouse_goods SET price = ? WHERE id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                List<UUID> updatedGoodsId = new ArrayList<>();
                int count = 0;
                while (resultSet.next()) {
                    // Увеличение цены товара на 10%
                    double newPrice = resultSet.getDouble("price") * (100 + percentage) / 100;
                    updateStatement.setDouble(1, newPrice);
                    updateStatement.setObject(2, UUID.fromString(resultSet.getObject("id").toString()));
                    updateStatement.addBatch();
                    updatedGoodsId.add(resultSet.getObject("id", UUID.class));

                    // Выполнение пакетного обновления, когда список обновленных объектов достигнет размера порции
                    if (++count % BATCH_SIZE == 0) {
                        updateStatement.executeBatch();
                        connection.commit();
                        outputBatch(updatedGoodsId, writer);
                        updatedGoodsId.clear();
                        writer.flush();
                        log.info("Updated " + count + " rows");
                    }
                }
            } catch (Exception e) {
                connection.rollback();
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void outputBatch(List<UUID> updatedGoodsId, BufferedWriter writer) throws IOException {
        for (UUID id : updatedGoodsId) {
            writer.write(id.toString() + "\n");
        }
    }
}
