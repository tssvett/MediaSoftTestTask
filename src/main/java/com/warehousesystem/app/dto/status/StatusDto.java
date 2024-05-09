package com.warehousesystem.app.dto.status;

import com.warehousesystem.app.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {

    @NotNull(message = "Status can not be null")
    @Enumerated(EnumType.STRING)
    private Status status;
}
