package com.chinaredstar.Controller;

import com.chinaredstar.api.IOrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * Created by huanhuan.jin on 2016/12/20.
 */
@Controller
public class OrderController {
    private Logger logger = Logger.getLogger(OrderController.class);
//    @Autowired
//    private IOrderService orderService;
    @RequestMapping(value = {"/"})
    public String index() {
        return "index";
    }
}
