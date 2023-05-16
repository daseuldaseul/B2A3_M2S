package B2A3_M2S.mes.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity(name = "company")
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor
public class Company extends BaseTimeEntity{

    @Id
    @Column(name = "company_cd")
    private String companyCd;   //업체 코드

    private String companyNm;   //업체 이름

    private String phoneNumber;      //연락처

    private String address;     //주소

    private String remark;      //비고

    //공통코드
    private String companyGb;   //업체 구분

}

