package com.demo.demo.common.advice;

import com.demo.demo.common.web.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * 方法执行前，可以设置值，取值方式：
     * 1.在参数上打上注解：@ModelAttribute("goal")String goal
     * 2.方法中增加一个ModelMap类型的参数即可
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("goal", "test");
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) {

        logger.error(request.getRequestURI(), e);

        response.setStatus(200);

        //业务异常直接返回异常信息
        if (e instanceof BusinessException) {
            return Result.fail(e.getMessage());
        }

        return Result.fail("服务器在开小差，请稍后再试！");
    }


}
