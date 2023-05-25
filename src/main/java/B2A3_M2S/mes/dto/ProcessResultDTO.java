package B2A3_M2S.mes.dto;

import lombok.Data;

@Data
public class ProcessResultDTO {
    //  0 : 미사용
    //  1 : 사용중
    private int state;
    private ItemDto item;
    private int inputQty;
    private int returnQty;

    //  0 : 실패
    //  1 : 완료
    private int result;

    public ProcessResultDTO() {
        this.state = 0;
        this.item = null;
        this.inputQty = 0;
        this.returnQty = 0;
        this.result = 0;
    }

}
