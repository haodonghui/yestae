/*
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yestae.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.yestae.common.utils.*;
import com.yestae.common.utils.excel.Excel2003;
import com.yestae.common.utils.excel.Excel2007;
import com.yestae.common.utils.excel.ExcelSheet;
import com.yestae.modules.fields.entity.SysFieldsAspectEntity;
import com.yestae.modules.sys.entity.SysUserEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.*;

public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public AbstractController() {
    }

    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return this.getUser().getUserId();
    }

    protected void transform(Map<String, Object> params) {
        if (params.get("params") != null) {
            params = (Map) JSONObject.parse(MapUtils.getString(params, "params"));
        }

    }

    protected void downloadXls(List<ExcelSheet> sheets, String flieName) {
        this.downloadXlsx(sheets, flieName);
    }

    protected void downloadXlsx(List<ExcelSheet> sheets, String flieName) {
        HttpServletRequest request = getHttpServletRequest();
        HttpServletResponse response = getHttpServletResponse();

        try {
            Excel2007 excel2007 = new Excel2007(sheets);
            ByteArrayOutputStream xlsOutputStream = excel2007.getOutputStream();
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + new String(flieName.getBytes("UTF-8"), "ISO8859-1") + ".xlsx");
            response.setContentType("application/msexcel;charset=utf-8");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
            IOUtils.write(xlsOutputStream.toByteArray(), output);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    protected void downloadTxt(List<ExcelSheet> sheets, String flieName) {
        HttpServletRequest request = getHttpServletRequest();
        HttpServletResponse response = getHttpServletResponse();

        try {
            Txt txt = new Txt(sheets);
            ByteArrayOutputStream xlsOutputStream = txt.getOutputStream();
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + new String(flieName.getBytes("UTF-8"), "ISO8859-1") + ".txt");
            response.setContentType("text/plain;charset=utf-8");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
            IOUtils.write(xlsOutputStream.toByteArray(), output);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    protected String downloadXlsBase64(List<ExcelSheet> sheets) {
        try {
            Excel2003 excel2003 = new Excel2003(sheets);
            ByteArrayOutputStream xlsOutputStream = excel2003.getOutputStream();
            return (new BASE64Encoder()).encode(xlsOutputStream.toByteArray());
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public R list(PageUtils page, String moduleCode, List<String> fields) {
        Map<String, String> dictionaryIds = new HashMap();
        if (fields == null) {
            fields = new ArrayList();
            List<SysFieldsAspectEntity> outputFields = FieldsAspectCache.get(this.getUser().getRoleIdList(), moduleCode, FieldsAspectCache.outputFields);
            Iterator iter = outputFields.iterator();

            while (iter.hasNext()) {
                SysFieldsAspectEntity sfa = (SysFieldsAspectEntity) iter.next();
                ((List) fields).add(sfa.getOutputCode());
                String dictionaryId = sfa.getField().getDictionaryId();
                if (StringUtils.isNotBlank(dictionaryId)) {
                    dictionaryIds.put(sfa.getOutputCode(), dictionaryId);
                }
            }
        }

        if (fields != null && ((List) fields).size() != 0) {
            List<Map<String, Object>> newList = new ArrayList();
            List<Map<String, Object>> rList = bean2MapList(page.getList(), (List) fields);
            Iterator iterator = rList.iterator();

            while (iterator.hasNext()) {
                Map<String, Object> newMap = new LinkedMap();
                Map<String, Object> map = (Map) iterator.next();
                Iterator iterator2 = map.keySet().iterator();

                while (iterator2.hasNext()) {
                    String key = (String) iterator2.next();
                    Object value = map.get(key);
                    if (((List) fields).contains(key)) {
                        if (dictionaryIds.get(key) != null) {
                            value = DictCache.getLabel((String) dictionaryIds.get(key) + "", value + "");
                        }

                        newMap.put(key, value);
                    }
                }

                newList.add(newMap);
            }

            page.setList(newList);
            return R.ok().put("page", page);
        } else {
            return R.ok().put("page", page);
        }
    }

    public static List<Map<String, Object>> bean2MapList(List<?> list, List<String> fields) {
        List<Map<String, Object>> rList = new ArrayList();

        Map map;
        for (Iterator iterator = list.iterator(); iterator.hasNext(); rList.add(map)) {
            Object obj = iterator.next();
            new LinkedMap();
            if (obj instanceof Map) {
                map = (Map) obj;
            } else {
                map = bean2Map(obj, fields);
            }
        }

        return rList;
    }

    public static Map<String, Object> bean2Map(Object obj, List<String> fields) {
        if (obj == null) {
            return null;
        } else {
            LinkedMap map = new LinkedMap();

            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                PropertyDescriptor[] var5 = propertyDescriptors;
                int var6 = propertyDescriptors.length;

                for (int var7 = 0; var7 < var6; ++var7) {
                    PropertyDescriptor property = var5[var7];
                    String key = property.getName();
                    if (fields.contains(key) && !key.equals("class")) {
                        Method getter = property.getReadMethod();
                        Object value = getter.invoke(obj);
                        if (value instanceof Date) {
                            value = DateUtils.dateToString((Date) value, "yyyy-MM-dd hh:mm:ss");
                        }

                        map.put(key, value);
                    }
                }
            } catch (Exception var12) {
                System.out.println("transBean2Map Error " + var12);
            }

            return map;
        }
    }

    public static void map2Bean2(Map<String, Object> map, Object obj) {
        if (map != null && obj != null) {
            try {
                BeanUtils.populate(obj, map);
            } catch (Exception var3) {
                System.out.println("transMap2Bean2 Error " + var3);
            }

        }
    }

    public static void map2Bean(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor[] var4 = propertyDescriptors;
            int var5 = propertyDescriptors.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                PropertyDescriptor property = var4[var6];
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }
            }
        } catch (Exception var11) {
            System.out.println("transMap2Bean Error " + var11);
        }

    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
    }

    public List<SysFieldsAspectEntity> getOutputFields(String moduleCode) {
        return FieldsAspectCache.get(this.getUser().getRoleIdList(), moduleCode, FieldsAspectCache.exportFields);
    }
}
*/
