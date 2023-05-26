package B2A3_M2S.mes.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NumPrefix implements PrefixMapper {
    
    OBTAIN_ORDER("SO"),     // 수주
    PURCHASE_ORDER("PO"),   // 발주
    RECEIVING("WI"),        // 입고
    RELEASE("WO"),          // 출고
    PRODUCTION("PP"),       // 지시
    PERFORMANCE("WL"),       // 지시
    SHIP("IR");             // 출하

    private final String title;

    @Override
    public String getCode() {
        return name();
    }
}
