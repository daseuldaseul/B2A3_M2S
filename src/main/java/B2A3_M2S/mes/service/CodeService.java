package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.CommonCodeDTO;
import B2A3_M2S.mes.entity.CommonCode;
import B2A3_M2S.mes.entity.CommonCodePK;

import java.util.List;

public interface CodeService {
    CommonCodePK register(CommonCodeDTO dto) throws Exception;

    void modify(CommonCodeDTO dto) throws Exception;

    void remove(CommonCodePK codeId);

    List<CommonCode> getGroupList(String useYn);

    List<CommonCode> getCodeList(String codeGroup, String useYn);

    default CommonCode dtoToEntity(CommonCodeDTO dto) {
        CommonCode entity = CommonCode.builder()
                .codeId(dto.getCodeId())
                .displayValue(dto.getDisplayValue())
                .codeSort(dto.getCodeSort())
                .useYn(dto.getUseYn())
                .remark(dto.getRemark())
                .build();

        return entity;
    }

    default CommonCodeDTO entityToDto(CommonCode entity) {
        CommonCodeDTO dto = CommonCodeDTO.builder()
                .codeId(entity.getCodeId())
                .displayValue(entity.getDisplayValue())
                .codeSort(entity.getCodeSort())
                .useYn(entity.getUseYn())
                .remark(entity.getRemark())
                .build();
        return dto;
    }
}
