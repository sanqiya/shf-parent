package com.atguigu.base;

/**
 * 项目名: shf-parent
 * 文件名: BaseController
 * 创建者: 曹勇
 * 创建时间:2022/6/17 20:37
 * 描述: TODO
 **/
import com.github.pagehelper.StringUtil;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

public class BaseController {

    private final static String PAGE_SUCCESS = "common/successPage";
    //提示信息
    public final static String MESSAGE_SUCCESS = "操作成功！";

    /**
     * 成功页
     * @param message
     * @param request
     */
    protected String successPage(String message, HttpServletRequest request) {
        request.setAttribute("messagePage", StringUtil.isEmpty(message) ? MESSAGE_SUCCESS : message);
        return PAGE_SUCCESS;
    }

    protected Map<String, Object> getFilters(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, Object> filters = new TreeMap();
        while(paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            if (values != null && values.length != 0) {
                if (values.length > 1) {
                    filters.put(paramName, values);
                } else {
                    filters.put(paramName, values[0]);
                }
            }
        }
        if(!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }
        if(!filters.containsKey("pageSize")) {
            filters.put("pageSize", 10);
        }

        return filters;
    }

}
