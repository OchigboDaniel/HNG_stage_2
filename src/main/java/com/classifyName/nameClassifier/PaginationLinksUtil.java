package com.classifyName.nameClassifier;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class PaginationLinksUtil {

    public static Map<String, Object> buildLinks(
            HttpServletRequest request,
            int page,
            int limit,
            int totalPages
    ) {
        String baseUrl = request.getRequestURI();

        Map<String, Object> links = new HashMap<>();

        // self
        links.put("self", baseUrl + "?page=" + page + "&limit=" + limit);

        // next
        if (page < totalPages) {
            links.put("next", baseUrl + "?page=" + (page + 1) + "&limit=" + limit);
        } else {
            links.put("next", null);
        }

        // prev
        if (page > 1) {
            links.put("prev", baseUrl + "?page=" + (page - 1) + "&limit=" + limit);
        } else {
            links.put("prev", null);
        }

        return links;
    }
}
