package com.mooc.api.controller;

import java.util.Map;

import com.mooc.api.common.ResultMsg;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ControllerHelper {

    public static void addFlashAttributes(RedirectAttributes redirectAttributes, Map<String, String> map) {
        map.forEach((key, value) -> redirectAttributes.addFlashAttribute(key, value));
    }

    public static void addFlashAttributes(RedirectAttributes redirectAttributes, ResultMsg resultMsg) {
        addFlashAttributes(redirectAttributes, resultMsg.asMap());
    }

}
