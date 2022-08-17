package com.ratepay.client.bugtracker.models;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String type;
    @NotBlank
    private String priority;
}
