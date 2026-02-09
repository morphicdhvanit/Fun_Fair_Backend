package com.funfair.api.account.userrole;

import java.time.LocalDateTime;
import java.util.List;

import com.funfair.api.event.gatenodetails.DoorGateDetailsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDoorManagerDetailsDto {

    private String userId;
    private String userName;
    private String userPhoneNumber;
    private String userEmail;
    private String userImage;

    private LocalDateTime roleStartDateTime;
    private LocalDateTime roleEndDateTime;

    private String orgId;
    private String eventId;

    private List<DoorGateDetailsDto> gates;

}
