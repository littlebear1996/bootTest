package com.chinaredstar.Controller;

import com.chinaredstar.api.IOrderService;
import com.chinaredstar.api.util.Page;
import com.chinaredstar.api.vo.OrderVo;
import com.chinaredstar.api.vo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;



/**
 * Created by huanhuan.jin on 2016/12/20.
 */
@Controller
public class OrderController {
    private Logger logger = Logger.getLogger(OrderController.class);
    @Autowired
    private IOrderService orderService;
    /**
     * 分页获取某个用户的所有订单
     * @param pageNo:页码
     * @param pageSize:行数
     */
    @RequestMapping("/showOrder")
    public String getAllOrder(HttpSession hs,Model model,Integer pageNo,Integer pageSize) {
        User user = (User) hs.getAttribute("user");
        /**
         * 如果重新设置了pageSize则保存在session中，防止再次传入的pageSize为null时又设置为默认值3
         */
        if(pageSize != null){
            hs.setAttribute("pageSize",pageSize);
        }else if(hs.getAttribute("pageSize")!=null){
            pageSize = (Integer) hs.getAttribute("pageSize");
        }
        Page page = new Page(orderService.getCountByName(user.getUserName()),pageNo,pageSize);
        model.addAttribute("maxPage",page.getPages());
        model.addAttribute("currPage",page.getPageNo());
        model.addAttribute("userName",user.getUserName());
        model.addAttribute("orderList",orderService.getOrderByName(user.getUserName(),page));
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
