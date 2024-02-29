package com.assistant.main.helpers;

import java.util.HashMap;
import java.util.Map;

public class APIParams {

    public Map<String, String> buildParams() {
        Map<String, String> params = new HashMap<>();
        params.put("appTypeId", "5");
        params.put("langId", "31");
        params.put("timezoneName", "America/Sao_Paulo");
        params.put("userCountryId", "21");
        return params;
    }
}
