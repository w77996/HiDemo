package com.w77996.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Controller
@RequestMapping(value = "/alipay")
@Slf4j
public class AlipayController {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016101200667662";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCBhxg7IFjM+8yle4D3FlqQMYenMocVyDUYPEKD2kd5il9LTsbV9VTeDPpSiZVN2TgfAkBWidWe35grAaEOhWMF10oK/UPsH6/3UiWBMpqUmk1SDo4TUVtkmBe91U94SLsxFEdXESGLHZtdvIan1rmCp2p53vuVextsvgTg1u7TIBEi+7UxNTN28wBupyJX07vy6+T8kxEIAxgoxL8FXVaqcEizQgSSwF9QAM9N8XPhpDumLZLUg5sv8QPUedOdLqnf0x00gfK2o1e0SITtF11gM+7Hror7TvNDXyqKCENC4LomMsnvHJSzP29ROJzyrRES7pFd4NRudzKGiP0MOsiDAgMBAAECggEACr5l6CGavAX2x5Mi81vLT0KMefXa9peQr93vtngYjaBInzp9i6m5zGFPMiFvXPGzkf7hz0vHiFILkSWlckrmF8nvvlGbnK5ULjOGIN1NrR3PZAsJBdktt35WGczjtppxzHReJn6pRZzDEldc4/cPlCLG1i0EuTVo5PAt/2hUjvZt+1vbMJlZdwt6By92+iL5WyOUu2Am/zvjdJurGnaLZnB4m371+mVgQbG3k8uqLMl3eHgXglaUxODa36dgS+naPk7lkOSFEVDw7CG92fyHvotwGJbfp9RyvbGFIurjFeYdSUrFpZ1IxBmDtiTnyVu03I+Kv5wTeZUf8BHo8mwcIQKBgQD/96T/DtY8pP353oDgCQ6IpPePt1Ll9aolu2AFrEOCOdzrz5eKUT7aQTYlDKof+vAkdHSB3YGfRJjpEyFy2p7d3MrUYat3KP8QzRzBOuJo2+1ARGoM071hW2CG5575qUnUKVDfEZ/hBRAoNbak2OsKlBn8BSUnLzhPrhfgo79uVwKBgQCBi1KitdedUvLnKuwjVsdL4cii7f3v84Gyi5MT2wBPxzvQXS/3FXT2UHDwcnrZb9tCng73O0n8IhWeyAVOtaatTKIA3+z+w2wJQRaSu1Mzql26MM7vndzMY751dlSpEYbszBdG9jbkNzaiG6rBXZiqvquM6OT5RZ9DDFiTEWdDtQKBgQCvUJei1i+oE72cYWhVif55WrvwpDhUJsGl3EN1PzpxvF3y6Rk69FCfPiDumVEAXGgUH+sYc3Vvd5Vftemn99Lt1GbbthWxpNOmV0YFluYxucgyx2j3ZiMExohDferlxqw5cx7f2ftuLICXecqh96xsb2mosoFy6CWidXQHOUBJOwKBgDcHuFLmbHGoiwcodKeOdZsGjvSnpPgJuU7fe9szbS52tDIPZBDSTJ1WDqQV/ZngqoPiIPlldkhlMkoBLkABaoyzwR4eAc83tO05eR1b9jw8YxR1J3Mi3Gj29jFv0wb/KxHsoPbEIQ1RZyP2HNOHrxLZbIavaJV7vev9kqLnAbcBAoGBAMyjzAptMn3hJwpTUR3Dqhk2Vk5zKpNK+CNng/HFpu+sL9rSWtWaYw+veI1mL2vQPPCO/9hRU3iP6qQ2ln7P8CpxGkwwZuOrFGLzEY4w4h7Qm+kw1ZsYzhbHLIRRCQwoG8Y2qcEvgUQQo0ND847VFU7j9PcDJq/3fokEaJ7WPXYk";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtaQnOuiMGArF2vMFEJhf2lsyawAm6MEDhOvB0tef8dg2wI8mSrVmyQBsJmSCI5VG4OtMh7mIAqRKB+Oo7DImGEMzeiS6RxjhYkYX1KA1cleo4mWT2H3OfclJZzwcooh7efwVUFV/wuupf28lHfEWSgglUExuYtW0yGDWHWS7tD0X4hrSd7JShP2lAXIkDEll0JJHwkvupy1mI3B/U9TNeVw2Cod5x6ctVFbkNKDmm8qPkZ04jjooQbI7YMSY1UkP05K0dBwrkeTwqtGYh27qmV7UUU56+F8bh+AiXPFLxwQYsOJoT4NuY0Brs3HejjdhvzzDR/S8b5+TnGZTTAtPhQIDAQAB";

    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://116.62.144.245/alipay/notifyUrl";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://116.62.144.245/alipay/returnUrl";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    //沙箱网关
    public static String gatewayUrl ="https://openapi.alipaydev.com/gateway.do";

    // 仅支持JSON
    public static String format = "JSON";

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public void pay(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws ServletException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, format, charset,
                alipay_public_key, sign_type);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);
        alipayRequest.setBizContent("{" + "    \"out_trade_no\":\"2015032001010100"+(int)(Math.random()*1000)+"\","
                + "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," + "    \"total_amount\":"+(int)(Math.random()*10)+0.88+","
                + "    \"subject\":\"Iphone6 16G\"," + "    \"body\":\"Iphone6 16G 耐克金\","
                + "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","
                + "    \"extend_params\":{" + "    \"sys_service_provider_id\":\"2088511833207846\"" + "    }" + "  }");// 填充业务参数
        String form = "";
//        AlipayTradePayRequest request = new AlipayTradePayRequest();
//        AlipayTradePayModel model = new AlipayTradePayModel();
//
//        request.setBizModel(model);
//        model.setOutTradeNo(System.currentTimeMillis()+"");
//        model.setTotalAmount("88.88");
//        model.setSubject("Iphone6 16G");
        try {
//            AlipayTradePayResponse response = alipayClient.execute(request);
//            System.out.print(response.getBody());
//            System.out.print(response.getQrCode());
            form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + charset);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @ApiOperation(value = "支付异步回调", notes = "支付宝")
    @RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response)
            throws AlipayApiException, IOException {
        System.out.println("#################################异步回调######################################");

        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);
        boolean signVerified = AlipaySignature.rsaCheckV2(params, alipay_public_key, charset, sign_type); // 调用SDK验证签名

        // ——请在这里编写您的程序（以下代码仅作参考）——

        /*
         * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
         * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）， 3、校验通知中的seller_id（或者seller_email)
         * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
         * 4、验证app_id是否为该商户本身。
         */
        if (signVerified) {// 验证成功
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("交易状态="+trade_status);
            if (trade_status.equals("TRADE_FINISHED")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序

                // 注意：
                // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序

                // 注意：
                // 付款完成后，支付宝系统发送该交易状态通知
            }

            System.out.println("异步回调验证成功");
            response.getWriter().write("success");

        } else {// 验证失败
            System.out.println("异步回调验证失败");
            response.getWriter().write("fail");

            // 调试用，写文本函数记录程序运行情况是否正常
            // String sWord = AlipaySignature.getSignCheckContentV1(params);
            // AlipayConfig.logResult(sWord);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    @ApiOperation(value = "支付同步回调", notes = "支付宝")
    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public void returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        System.out.println("=================================同步回调=====================================");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);
        boolean signVerified =  AlipaySignature.rsaCheckV1(params, alipay_public_key, charset, sign_type);

        // ——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+total_amount);

            response.getWriter().write(
                    "trade_no:" + trade_no + "<br/>out_trade_no:" + out_trade_no + "<br/>total_amount:" + total_amount);
        } else {
            response.getWriter().write("验签失败");
        }
        response.getWriter().flush();
        response.getWriter().close();
    }
}
