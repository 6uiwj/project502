package org.choongang.file.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.BaseMember;

import java.util.UUID;

//업로드한 사람만 삭제 가능
@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = {
        @Index(name="idx_fInfo_gid",columnList = "gid"),
        @Index(name="idx_fInfo_gid_loc", columnList = "gid,location")
})
public class FileInfo extends BaseMember {
    @Id @GeneratedValue
    private Long seq; //파일 등록 번호, 서버에 업로드하는 파일명 기준
    @Column(length=65, nullable = false)
    private String gid = UUID.randomUUID().toString(); //랜덤하게 유니크아이디를 만들 수 있는 편의기능(문자열반환)

    @Column(length=65)
    private String location;

    @Column(length=100)
    private String fileName;

    @Column(length=30)
    private String extension;

    private boolean done;
}
