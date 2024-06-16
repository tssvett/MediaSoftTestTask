package com.warehousesystem.app.businesslogic.account;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface AccountServiceClient {

    CompletableFuture<Map<String, String>> getAccountsByLogins(List<String> logins);
}
