package com.cos.blog.model;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "connected_at",
        "properties",
        "kakao_account"
})
@Generated("jsonschema2pojo")
@Data
public class KakaoProfile {

    @JsonProperty("id")
    public Long id;
    @JsonProperty("connected_at")
    public String connectedAt;
    @JsonProperty("properties")
    public Properties properties;
    @JsonProperty("kakao_account")
    public KakaoAccount kakaoAccount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "nickname",
            "profile_image",
            "thumbnail_image"
    })
    @Generated("jsonschema2pojo")
    @Data
    public class Properties {

        @JsonProperty("nickname")
        public String nickname;
        @JsonProperty("profile_image")
        public String profileImage;
        @JsonProperty("thumbnail_image")
        public String thumbnailImage;

    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "profile_nickname_needs_agreement",
            "profile_image_needs_agreement",
            "profile",
            "has_email",
            "email_needs_agreement",
            "is_email_valid",
            "is_email_verified",
            "email"
    })
    @Generated("jsonschema2pojo")
    @Data
    public class KakaoAccount {

        @JsonProperty("profile_nickname_needs_agreement")
        public Boolean profileNicknameNeedsAgreement;
        @JsonProperty("profile_image_needs_agreement")
        public Boolean profileImageNeedsAgreement;
        @JsonProperty("profile")
        public Profile profile;
        @JsonProperty("has_email")
        public Boolean hasEmail;
        @JsonProperty("email_needs_agreement")
        public Boolean emailNeedsAgreement;
        @JsonProperty("is_email_valid")
        public Boolean isEmailValid;
        @JsonProperty("is_email_verified")
        public Boolean isEmailVerified;
        @JsonProperty("email")
        public String email;


        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonPropertyOrder({
                "nickname",
                "thumbnail_image_url",
                "profile_image_url",
                "is_default_image"
        })
        @Generated("jsonschema2pojo")
        @Data
        public class Profile {

            @JsonProperty("nickname")
            public String nickname;
            @JsonProperty("thumbnail_image_url")
            public String thumbnailImageUrl;
            @JsonProperty("profile_image_url")
            public String profileImageUrl;
            @JsonProperty("is_default_image")
            public Boolean isDefaultImage;

        }
    }
}
