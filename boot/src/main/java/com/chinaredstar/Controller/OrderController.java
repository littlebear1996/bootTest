package com.chinaredstar.Controller;

import com.chinaredstar.api.IOrderService;
import com.chinaredstar.api.vo.OrderVo;
import com.chinaredstar.api.vo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;


/**
 * Created by huanhuan.jin on 2016/12/20.
 */
@Controller
public class OrderController {
    private Logger logger = Logger.getLogger(OrderController.class);
    @Autowired
    private IOrderService orderService;
    /**
     * 获取某个用户的所有订单
     */
    @RequestMapping("/showOrder")
    public String getAllOrder(HttpSession hs,Model model) {
        User user = (User) hs.getAttribute("user");
        model.addAttribute("userName",user.getUserName());
        model.addAttribute("orderList",orderService.getOrderByName(user.getUserName()));
        return "/showOrder";
    }

    /**
     * 跳转到添加订单页面
     */
    @RequestMapping("/toAddOrder")
    public String toAddOrder(ModelMap model,HttpSession hs) {
        User user = (User) hs.getAttribute("user");
        OrderVo orderVo = new OrderVo();
        orderVo.setUser(user.getUserName());
        model.addAttribute("orderVo", orderVo);
        return "/addOrder";
    }

    /**
     * 添加订单并重定向到show页面
     */
    @RequestMapping("/addOrder")
    public String addOrder(@Valid OrderVo orderVo, BindingResult result
    ) {
        if (!result.hasErrors()) {
            orderService.addOrder(orderVo);
            return "redirect:/showOrder";
        } else {
            return "/addOrder";
        }
    }

    /**
     * 通过orderId查找单个用户
     */
    @RequestMapping("/getOrder")
    public String getOrder(int orderId,Model model){
		model.addAttribute("orderVo", orderService.getOrderById(orderId));
		return "/editOrder";
	}
    /**
     * 修改订单
     */
    @RequestMapping("/updateOrder")
    public String updateOrder(@Valid OrderVo orderVo,
                              BindingResult result) {
        if (!result.hasErrors()) {
            orderService.updateOrder(orderVo);
            return "redirect:/showOrder";
        } else {
            return "/editOrder";
        }
    }

    /**
     * 通过订单编号删除订单
     *
     * @param orderId
     * @return :
     */
    @RequestMapping("/delOrder")
    public String delOrder(int orderId) {
        orderService.delOrderById(orderId);
        return "redirect:/showOrder";
    }
}
