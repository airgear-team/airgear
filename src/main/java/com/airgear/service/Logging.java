package com.airgear.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Logging {

    void log(HttpServletRequest request);

    void log(HttpServletResponse response);

}
