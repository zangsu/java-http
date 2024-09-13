package org.apache.coyote.http11.httpmessage.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.apache.coyote.http11.exception.IllegalHttpMessageException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HttpHttpRequestLineTest {

    @Nested
    @DisplayName("파싱 테스트")
    class ParseFromTest {
        @Test
        @DisplayName("파싱 성공")
        void success() {
            // given
            String requestLineText = "GET /index.html HTTP/1.1 ";

            // when
            HttpRequestLine httpRequestLine = HttpRequestLine.parseFrom(requestLineText);

            //then
            assertAll(
                    () -> assertThat(httpRequestLine.httpVersion()).isEqualTo("HTTP/1.1"),
                    () -> assertThat(httpRequestLine.httpMethod()).isEqualTo(HttpMethod.GET),
                    () -> assertThat(httpRequestLine.target()).isEqualTo("/index.html")
            );
        }

        @Test
        @DisplayName("잘못된 형식 파싱 실패")
        void IllegalFormTest() {
            // given
            String wrongRequestLine = "GET /index.html";

            // when & then
            assertThatThrownBy(() -> HttpRequestLine.parseFrom(wrongRequestLine))
                    .isInstanceOf(IllegalHttpMessageException.class)
                    .hasMessageContaining("잘못된 헤더 형식입니다.");
        }
    }
}
