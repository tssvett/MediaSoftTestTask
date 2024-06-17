package com.warehousesystem.app.businesslogic.integration.crm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface CrmServiceClient {

    CompletableFuture<Map<String, String>> getInnsByLogins(List<String> logins);
}
