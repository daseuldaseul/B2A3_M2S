package B2A3_M2S.mes.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Getter
public enum NumPrefix implements PrefixMapper {
    
    OBTAIN_ORDER("SO"),     // 수주
    PURCHASE_ORDER("PO"),   // 빌주
    RECEIVING("SI"),        // 입고
    RELEASE("WO"),          // 출고
    PRODUCTION("PP"),       // 생산
    SHIP("IR");             // 출하

    private final String title;

    @Override
    public String getCode() {
        return name();
    }
}
