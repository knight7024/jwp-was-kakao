package model;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ToString
@NoArgsConstructor
public class HttpRequest {
    private StatusLine statusLine;
    private final Headers headers = new Headers();

    @ToString
    @Builder
    private static class StatusLine {
        private final String method;
        private final String path;
        private final String protocolVersion;
    }

    @ToString
    private static class Headers {
        private final Map<String, String> headers = new HashMap<>();

        public void put(String key, String value) {
            headers.put(key, value);
        }

        public String get(String key) {
            return headers.get(key);
        }
    }

    public HttpRequest setStatusLine(final String statusLine) {
        String[] temp = statusLine.split(" ");
        this.statusLine = StatusLine.builder()
                .method(temp[0])
                .path(temp[1])
                .protocolVersion(temp[2])
                .build();
        return this;
    }

    public String getMethod() {
        return this.statusLine.method;
    }

    public String getURI() {
        return this.statusLine.path;
    }

    public String getVersion() {
        return this.statusLine.protocolVersion;
    }

    public HttpRequest addHeader(final String header) {
        String[] temp = header.split(": ");
        return putHeader(temp[0], temp[1]);
    }

    public HttpRequest putHeader(final String key, final String value) {
        headers.put(key, value);
        return this;
    }

    public Optional<String> getHeader(final String key) {
        return Optional.ofNullable(headers.get(key));
    }
}