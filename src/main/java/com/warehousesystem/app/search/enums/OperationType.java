package com.warehousesystem.app.search.enums;

public enum OperationType {
EQUALS,
LIKE,
RIGHT_LIMIT,
LEFT_LIMIT;


public static OperationType getOperation(String operation) {
    return switch (operation) {
        case "LIKE", "~" -> OperationType.LIKE;
        case "EQUAL", "=" -> OperationType.EQUALS;
        case "GREATER_THAN_OR_EQUAL", ">=" -> OperationType.LEFT_LIMIT;
        case "<=", "LESS_THAN_OR_EQUAL" -> OperationType.RIGHT_LIMIT;
        default -> throw new IllegalStateException("Unexpected value: " + operation);
        };
    }
}
