package spring.toy.dto.oauth2;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;
    private final Map<String, Object> attributeAccount;
    private final Map<String, Object> attributeProfile;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
        this.attributeAccount = (Map<String, Object>) attribute.get("kakao_account");
        this.attributeProfile = (Map<String, Object>) attributeAccount.get("profile");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return getName() + "@kakao.com";
    }

    @Override
    public String getName() {
        return attributeProfile.get("nickname").toString();
    }
}