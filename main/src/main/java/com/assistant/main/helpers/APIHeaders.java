package com.assistant.main.helpers;

import org.springframework.http.HttpHeaders;

public class APIHeaders {

    public HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authority", "webws.365scores.com");
        headers.add("accept", "*/*");
        headers.add("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7,fr-FR;q=0.6,fr;q=0.5,de-DE;q=0.4,de;q=0.3,ja-JP;q=0.2,ja;q=0.1,ko-KR;q=0.1,ko;q=0.1,es-ES;q=0.1,es;q=0.1,ru-RU;q=0.1,ru;q=0.1,ar-IL;q=0.1,ar;q=0.1,it-IT;q=0.1,it;q=0.1,he-IL;q=0.1,he;q=0.1,pt-PT;q=0.1,vi-VN;q=0.1,vi;q=0.1");
        headers.add("origin", "https://www.365scores.com");
        headers.add("referer", "https://www.365scores.com/");
        headers.add("sec-ch-ua", "\"Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122\"");
        headers.add("sec-ch-ua-mobile", "?0");
        headers.add("sec-ch-ua-platform", "macOS");
        headers.add("sec-fetch-dest", "empty");
        headers.add("sec-fetch-mode", "cors");
        headers.add("sec-fetch-site", "same-site");
        headers.add("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
        return headers;
    }
}
