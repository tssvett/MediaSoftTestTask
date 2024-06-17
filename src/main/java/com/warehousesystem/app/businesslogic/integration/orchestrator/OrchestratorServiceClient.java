package com.warehousesystem.app.businesslogic.integration.orchestrator;

import com.warehousesystem.app.dto.orchestrator.OrchestratorRequestDto;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface OrchestratorServiceClient {

    UUID startOrderConfirmProcess(OrchestratorRequestDto orchestratorRequestDto) throws ExecutionException, InterruptedException;
}
