package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.CodeSetDTO;
import B2A3_M2S.mes.dto.CommonCodeDTO;
import B2A3_M2S.mes.entity.CommonCode;
import B2A3_M2S.mes.entity.CommonCodePK;
import B2A3_M2S.mes.entity.QCommonCode;
import B2A3_M2S.mes.repository.CommonCodeRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // 의존성 자동주입
@Log4j2
public class CodeServiceImpl implements CodeService {
    private static CodeSetDTO codeSetDTO;
    private final CommonCodeRepository repository;

    @PostConstruct
    public void initCode() throws Exception {
        if (codeSetDTO == null) {
            synchronized (this) {
                if (codeSetDTO == null) {
                    codeSetDTO = new CodeSetDTO();
                    // 그룹 코드명 조회
                    codeSetDTO.setCodeGroup(repository.getByCodeGroupAndUseYn("0", "Y")
                            .stream()
                            .map(a -> entityToDto(a))
                            .collect(Collectors
                                    .toMap(CommonCodeDTO::getCd, Function.identity())
                            )
                    );
                    Set<String> keySet = codeSetDTO.getCodeGroup().keySet();
                    Map<String, List<CommonCodeDTO>> map = codeSetDTO.getCode();
                    for (String codeGroup : keySet) {
                        map.put(codeGroup, new ArrayList<CommonCodeDTO>());
                    }
                    repository.getByCodeGroupNotAndUseYn("0", "Y")
                            .stream()
                            .map(a -> entityToDto(a))
                            .forEach(dto -> map.get(dto.getCodeGroup()).add(dto));
                }
            }
        }
    }

    public static void clear() throws Exception {
        codeSetDTO.getCodeGroup().clear();
        codeSetDTO.getCode().clear();
    }

    /**
     * 그룹코드를 입력하여 그룹명을 조회하는 메소드입니다.
     *
     * @param "String groupCode 그룹 ID"
     * @return DisplayValue 그룹명
     */
    public static String getCodeGroupNm(String groupCode) {
        Map<String, CommonCodeDTO> group = codeSetDTO.getCodeGroup();
        String name = null;
        name = group.get(groupCode).getDisplayValue();
        return name;
    }

    /**
     * 입력한 그룹코드의 코드 List를 조회하는 메소드입니다.
     *
     * @param "String groupCode 코드그룹 ID"
     * @return "List<CommonCodeDTO> 코드List"
     */
    public static List<CommonCodeDTO> getCodeList(String groupCode) {
        Map<String, List<CommonCodeDTO>> codeMap = codeSetDTO.getCode();
        List<CommonCodeDTO> list = null;
        list = codeMap.get(groupCode);
        //list.stream().forEach(a -> a.setCode(a.getCd()));
        return list;
    }

    /**
     * 그룹코드와 코드를 입력하면 해당 코드에 대한 코드값을 리턴하는 메소드입니다.
     *
     * @param "String groupCode 코드그룹 ID"
     * @return String 코드값
     */
    public static String getCodeNm(String groupCode, String code) {
        Map<String, List<CommonCodeDTO>> codeMap = codeSetDTO.getCode();
        List<CommonCodeDTO> list = codeMap.get(groupCode);
        String codeNm = "";

        for (CommonCodeDTO dto : list) {
            if (dto.getCodeId().getCd().equals(code)) {
                codeNm = dto.getDisplayValue();
                break;
            }
        }
        return codeNm;
    }

    /**
     * 코드를 관리하는 CodeSet을 리턴하는 메소드입니다.
     *
     * @param ""
     * @return CodeSetDTO
     */
    public static CodeSetDTO getCodeSetDTO() {
        return codeSetDTO;
    }

    ///////////////////////////////////////////////////////////////////
    ////////////////////// 코드관리 페이지에서 사용////////////////////////
    //////////////////////////////////////////////////////////////////

    @Override
    public CommonCodePK register(CommonCodeDTO dto) throws Exception {
        CommonCode entity = dtoToEntity(dto);
        entity = repository.save(entity);
        CodeServiceImpl.clear();
        this.initCode();
        return entity.getCodeId();
    }

    @Override
    public void modify(CommonCodeDTO dto) throws Exception {
        Optional<CommonCode> result = repository.findById(dto.getCodeId());

        if (result.isPresent()) {
            CommonCode entity = dtoToEntity(dto);
            repository.save(entity);
            CodeServiceImpl.clear();
            this.initCode();
        }
    }

    @Override
    public void remove(CommonCodePK codeId) {
        repository.deleteById(codeId);
    }

    @Override
    public List<CommonCodeDTO> getGroupList(CommonCodeDTO dto) {
        BooleanBuilder builder = getSearch(dto);
        Iterable<CommonCode> temp = repository.findAll(builder);
        List<CommonCodeDTO> result = new ArrayList<>();

        for (CommonCode entity : temp)
            result.add(entityToDto(entity));

        return result;
    }

    @Override
    public List<CommonCodeDTO> getCodeList(String codeGroup, String useYn) {
        if (useYn == null)
            useYn = "";

        List<CommonCodeDTO> result = repository.getByCodeGroupAndUseYn(codeGroup, useYn).stream().map(a -> entityToDto(a)).collect(Collectors.toList());
        return result;
    }

    private BooleanBuilder getSearch(CommonCodeDTO dto) {
        QCommonCode qCommonCode = QCommonCode.commonCode;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qCommonCode.codeId.codeGroup.eq("0");
        builder.and(expression);

        // 검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        conditionBuilder.and(qCommonCode.codeId.cd.contains(dto.getCd()));

        if (dto.getDisplayValue() != null)
            conditionBuilder.and(qCommonCode.displayValue.contains(dto.getDisplayValue()));

        if (dto.getUseYn() != null && dto.getUseYn() != 'A' && String.valueOf(dto.getUseYn()).trim().length() != 0) {
            /*if (dto.getUseYn() != null && String.valueOf(dto.getUseYn()).trim().length() != 0) {*/
            conditionBuilder.and(qCommonCode.useYn.eq(dto.getUseYn()));
        }
        if (dto.getRegDate() != null && dto.getRegDateEnd() != null)
            //conditionBuilder.and(qCommonCode.regDate.between(dto.getRegDate().atStartOfDay(), dto.getRegDateEnd().atTime(LocalTime.MAX)));
            conditionBuilder.and(qCommonCode.regDate.between(dto.getRegDate(), dto.getRegDateEnd()));

        if (dto.getModDate() != null && dto.getModDateEnd() != null)
            //conditionBuilder.and(qCommonCode.regDate.between(dto.getModDate().atStartOfDay(), dto.getModDateEnd().atTime(LocalTime.MAX)));
            conditionBuilder.and(qCommonCode.regDate.between(dto.getModDate(), dto.getModDateEnd()));
        //모든 조건 통합
        builder.and(conditionBuilder);
        return builder;
    }
}
