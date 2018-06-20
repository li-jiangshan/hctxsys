package com.hctxsys.controller.examine;

import com.hctxsys.entity.YskMoneyGetEntity;
import com.hctxsys.service.MoneyGetServiceImpl;
import com.hctxsys.util.DateUtils;
import com.hctxsys.util.ExcelUtil;
import com.hctxsys.util.Result;
import com.mysql.jdbc.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class MoneyGetController {
    @Autowired
    private MoneyGetServiceImpl moneyGetService;

    // 查找未审核的数据

    /**
     * @param model
     * @param strstatus  0-未支付 1-已支付 2-不通过
     * @param page       页码
     * @param feetime    到账时间
     * @param type       1-现金提现 2-玉贝提现
     * @param date_start 开始时间
     * @param date_end   结束时间
     * @param querytype  搜索类型
     * @param keyword    搜索关键字
     * @return
     */
    @RequestMapping(value = {"/Admin/Money/index", "/Admin/Money/index/status/{strstatus}"})
    // @ResponseBody
    public String findByStatusEqualsZero(Model model, @PathVariable(required = false) String strstatus,
                                         @RequestParam(defaultValue = "0") Integer page, String feetime, String type, String date_start,
                                         String date_end, String querytype, String keyword) {
        byte status = 0;
        if (null != strstatus && 0 != strstatus.length()) {
            status = Byte.valueOf(strstatus);
        }
        Page<YskMoneyGetEntity> list = moneyGetService.findByStatusEquals(status, page, 10, feetime, "1", date_start,
                date_end, querytype, keyword);
        int rows = (int) list.getTotalElements();
        // 如果有数据
        if (rows != 0) {
            // List<PageTableDate> pageTableDates = new ArrayList<PageTableDate>();
            model.addAttribute("rows", rows);// 总记录数
            /*
             * int pagesize=rows/10+1; for(int i=0;i<pagesize;i++) { PageTableDate tableDate
             * = new PageTableDate(); //分页a标签的href String href =
             * moneyGetService.setHref("/Admin/Money/", status, i, feetime, type,
             * date_start, date_end, querytype, keyword); tableDate.setHref(href);
             * tableDate.setPage(i+1); pageTableDates.add(tableDate); }
             * model.addAttribute("pageTableDates", pageTableDates);
             */
        }
        // 生产上一页的href
        // String previousPage = moneyGetService.setHref("/Admin/Money/", status,
        // page-1, feetime, type, date_start, date_end, querytype, keyword);
        // 生产下一页的href
        // String nextPage = moneyGetService.setHref("/Admin/Money/", status, page+1,
        // feetime, type, date_start, date_end, querytype, keyword);
        model.addAttribute("pageCount", list.getTotalPages());// 总页数
        model.addAttribute("moneygetlist", list);
        model.addAttribute("status", status);
        model.addAttribute("feetime", feetime);
        model.addAttribute("type", type);
        model.addAttribute("date_start", date_start);
        model.addAttribute("date_end", date_end);
        model.addAttribute("querytype", querytype);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);// 当前页
        // model.addAttribute("previousPage", previousPage);
        // model.addAttribute("nextPage", nextPage);
        return "/admin/money/index";
    }

    @RequestMapping("Admin/Money/addExcel/{status}")
    public void addExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable String status, String date_start,
                         String date_end, String querytype, String keyword) {
        List<YskMoneyGetEntity> all = moneyGetService.getAll(Integer.valueOf(status), date_start,
                date_end, querytype, keyword);
        //excel标题
        String[] title = {"ID", "类型", "实际到账", "提现数量", "手续费", "到账时间", "用户", "收款账号", "时间", "状态"};

        //excel文件名
        String fileName = "提现信息表" + System.currentTimeMillis() + ".xls";
        String content[][] = new String[all.size()][];
        //sheet名
        String sheetName = "提现信息表";
        for (int i = 0; i < all.size(); i++) {
            content[i] = new String[title.length];
            YskMoneyGetEntity obj = all.get(i);
            content[i][0] = String.valueOf(obj.getId());
            content[i][1] = String.valueOf(obj.getTypeName());
            if (obj.getId() > 46) {
                switch (obj.getType()) {
                    case 1:
                        content[i][2] = String.valueOf(obj.getMoney().subtract(obj.getFee()));
                        break;
                    case 2:
                        content[i][2] = String.valueOf((obj.getMoney().subtract(obj.getFee())).divide(BigDecimal.valueOf(100)));
                        break;
                }
            } else {
                switch (obj.getType()) {
                    case 1:
                        content[i][2] = String.valueOf(obj.getMoney());
                        break;
                    case 2:
                        content[i][2] = String.valueOf(obj.getMoney().divide(BigDecimal.valueOf(100)));
                        break;
                }
            }
            content[i][3] = String.valueOf(obj.getMoney());
            content[i][4] = String.valueOf(obj.getFee());
            content[i][5] = String.valueOf("T+1个工作日到账");
            content[i][6] = String.valueOf("用户:" + obj.getUsername() + " 手机:" + obj.getMobile());
            if (StringUtils.isNullOrEmpty(obj.getBankBranch())) {
                content[i][7] = String.valueOf("用户:" + obj.getUserName() + " 银行名:" + obj.getBankName() + " 卡号:" + obj.getCardNo());
            } else {
                content[i][7] = String.valueOf("用户:" + obj.getUserName() + " 银行名:" + obj.getBankName() + "(" + obj.getBankBranch() + ")" + " 卡号:" + obj.getCardNo());
            }
            content[i][8] = String.valueOf(DateUtils.getStrTime(String.valueOf(obj.getCreateTime()), new SimpleDateFormat("yyyy-MM-dd HH:ss:dd")));
            switch (obj.getStatus()) {
                case 0:
                    content[i][9] = "待审核";
                    break;
                case 1:
                    content[i][9] = "已通过";
                    break;
                case 2:
                    content[i][9] = "已拒绝";
                    break;
                case 3:
                    content[i][9] = "已到账";
                    break;
            }
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /*
     * //查找审核完成的数据
     *
     * @RequestMapping("/Admin/money/index/status/{status}.html") //@ResponseBody
     * public String findByStatusEqualsOne(Model model, @PathVariable byte
     * status,@RequestParam(defaultValue="0")Integer page,String feetime,String
     * type,String date_start,String date_end,String querytype,String keyword){
     * Page<MoneyGet> list = moneyGetService.findByStatusEquals(status, page, 10,
     * feetime, type, date_start, date_end, querytype, keyword); int rows =
     * (int)list.getTotalElements(); //如果有数据 if(rows!=0) { List<PageTableDate>
     * pageTableDates = new ArrayList<PageTableDate>(); model.addAttribute("rows",
     * rows); int pagesize=rows/10+1; for(int i=0;i<pagesize;i++) { PageTableDate
     * tableDate = new PageTableDate(); String href =
     * moneyGetService.setHref("/Admin/money/", status, i, feetime, type,
     * date_start, date_end, querytype, keyword); tableDate.setHref(href);
     * tableDate.setPage(i+1); pageTableDates.add(tableDate); }
     * model.addAttribute("pageTableDates", pageTableDates); } //生产上一页的href String
     * previousPage = moneyGetService.setHref("/Admin/money/", status, page-1,
     * feetime, type, date_start, date_end, querytype, keyword); //生产下一页的href String
     * nextPage = moneyGetService.setHref("/Admin/money/", status, page+1, feetime,
     * type, date_start, date_end, querytype, keyword);
     * model.addAttribute("moneygetlist", list);
     * model.addAttribute("status",String.valueOf(status));
     * model.addAttribute("feetime", feetime); model.addAttribute("type", type);
     * model.addAttribute("date_start", date_start); model.addAttribute("date_end",
     * date_end); model.addAttribute("querytype", querytype);
     * model.addAttribute("keyword", keyword); model.addAttribute("page",page);
     * model.addAttribute("previousPage", previousPage);
     * model.addAttribute("nextPage", nextPage); return "/admin/money/index"; }
     */

    // 提交审核的对话框

    /**
     * @param reply  回复的信息
     * @param status 是否通过的状态
     * @param id     用户id
     * @return
     */
    @RequestMapping("/Admin/Money/check")
    @ResponseBody
    public Result checkMoneyGet(String reply, Byte status, Integer id, HttpServletRequest request) {
        Integer adminid = (Integer) request.getSession().getAttribute("uid");
        Result result = moneyGetService.updateMoneyGet(reply, status, id, adminid);
        return result;
    }

    // 新增修改状态

    /**
     * 点击确认到账将状态修改为3
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/Admin/Money/edit/id/{id}/uid/{uid}")
    @ResponseBody
    public Result updateedit(@RequestParam(defaultValue = "3") byte status, @PathVariable Integer id, @PathVariable Integer uid) {
        Result result = moneyGetService.updateStatus(status, id, uid);
        return result;
    }
}
