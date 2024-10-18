package kh.st.boot.info;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KakaoInfo {
    private Long id;
    private String nickname;
    private String email;
}
