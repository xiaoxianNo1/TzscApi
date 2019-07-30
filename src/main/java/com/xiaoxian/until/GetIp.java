package com.xiaoxian.until;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @Author: 小贤
 * @Date: 2019/3/4/ 0004 11:06
 * 获取ip地址
 * @Version 1.0
 */
public class GetIp {

    public static String getIpAddr(HttpServletRequest request){

        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("192.168.2.248")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 设置IP
     * 保存jykz的ip
     * @return ip
     */
    public static String szip(){
        return "0:0:0:0:0:0:0:1";
    }
}
