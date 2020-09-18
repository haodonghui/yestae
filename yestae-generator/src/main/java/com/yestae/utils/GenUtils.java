package com.yestae.utils;

import ch.qos.logback.classic.db.names.TableName;

import com.yestae.entity.ColumnEntity;
import com.yestae.entity.TableEntity;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GenUtils {
    public static final char UNDERLINE = '_';

    public static List<String> getTemplates(){
        List<String> templates = new ArrayList<String>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Dao.java.vm");
        templates.add("template/Dao.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
        templates.add("template/menu.sql.vm");

        templates.add("template/index.vue.vm");
        templates.add("template/add-or-update.vue.vm");

        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip,String subpackage,List<Map<String, Object>> sysFieldsSql) {
        //配置信息
        Configuration config = getConfig();
        
        if(StringUtils.isNotBlank(subpackage)){
        	config.setProperty("moduleName",subpackage);
        }
        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName" ));
        tableEntity.setComments(table.get("tableComment" ));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix" ));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for(Map<String, String> column : columns){
            ColumnEntity columnEntity = new ColumnEntity();
            String columnName = column.get("columnName");
            columnEntity.setColumnName(columnName);
            columnEntity.setDataType(column.get("dataType" ));
            String columnComment = column.get("columnComment");
            if(StringUtils.isBlank(columnComment)){
            	columnComment = columnName;
            }
            if(StringUtils.isNotBlank(columnComment) && columnComment.length()>10){
            	if(columnComment.indexOf(" ") < 10){
            		columnComment = StringUtils.substringBefore(columnComment, " ");
            	}else
            	if(columnComment.indexOf("-") < 10){
            		columnComment = StringUtils.substringBefore(columnComment, "-");
            	}else{
            		columnComment = columnComment.substring(0, 10);
            	}
            }
            columnEntity.setComments(columnComment);
            columnEntity.setExtra(column.get("extra" ));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType" );
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal" )) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey" )) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
        String mainPath = config.getString("mainPath" );
        mainPath = StringUtils.isBlank(mainPath) ? "com.yestae" : mainPath;
        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", mainPath);
        map.put("package", config.getString("package" ));
        map.put("moduleName", config.getString("moduleName" ));
        
        map.put("author", config.getString("author" ));
        map.put("email", config.getString("email" ));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));

        String titles = "";
        String fields = "";
        String selectFields = "{";
//		String []selectFields = {"id","dataTime","salesAmount","orderNum","newPcardNum","newGcardNum","newUserNum","userNum","gcardNum","depositInAmount","depositOutAmount","depositBalanceAmount"};
        for (Iterator iterator = columsList.iterator(); iterator.hasNext();) {
			ColumnEntity columnEntity2 = (ColumnEntity) iterator.next();
			String title = columnEntity2.getComments();
			String field = columnEntity2.getAttrname();
			titles = titles+title+",";
			fields = fields+field+",";
			selectFields = selectFields+"\""+field+"\",";
		}
        
        
        titles = StringUtils.substringBeforeLast(titles, ",");
        fields = StringUtils.substringBeforeLast(fields, ",");
        selectFields = StringUtils.substringBeforeLast(selectFields, ",")+"}";
        
        map.put("titles", titles);
        map.put("fields", fields);
        map.put("selectFields", selectFields);
        
        String []insertSqls = getInsertSql(sysFieldsSql,tableEntity);
        map.put("insertFieldsSql", insertSqls[0]);
        map.put("insertFieldsAspectSql", insertSqls[1]);
        
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8" );
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), config.getString("package" ), config.getString("moduleName" ))));
                IOUtils.write(sw.toString(), zip, "UTF-8" );
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }

    /**
	 * @param sysFieldsSql
	 * @param tableEntity
	 * @return
	 */
	private static String[] getInsertSql(List<Map<String, Object>> sysFieldsSql, TableEntity tableEntity) {
		String []insertSqls = new String[2];
		StringBuffer insertFieldsSqlBuf = new StringBuffer();
        StringBuffer insertFieldsAspectSqlBuf = new StringBuffer();
        for (int i = 0; i < sysFieldsSql.size(); i++) {
        	Map<String,Object> map1 = (Map<String,Object>) sysFieldsSql.get(i);
			String fieldId = UUID.randomUUID().toString().replace("-", "");
			String module_code = tableEntity.getClassname().toLowerCase();
			String table_name = MapUtils.getString(map1, "table_name");
			String column_name = MapUtils.getString(map1, "column_name");
			String column_comment = MapUtils.getString(map1, "column_comment");
			String data_type = MapUtils.getString(map1, "data_type");
			String column_length = MapUtils.getString(map1, "column_length");
			int sequence = MapUtils.getIntValue(map1, "sequence",0);
			int required = MapUtils.getIntValue(map1, "required",0);
			
			String insertFieldsSql = "INSERT INTO `sys_fields` VALUES ('"+fieldId+"', '"+module_code+"', '"+
					table_name+"', '"+column_name+"', '"+column_comment+"', '"+data_type+"', "+column_length+", '', '', "+
					sequence+", "+required+");";
			insertFieldsSqlBuf.append(insertFieldsSql+"\r\n");
			
			String column_name_camel = underlineToCamel(column_name);
			String id = UUID.randomUUID().toString().replace("-", "");
			String insertFieldsAspectSql = "INSERT INTO `sys_fields_aspect` VALUES("
					+ "'"+id+"','"+fieldId+"','"+module_code+"',null,null,"
					+ "0,"+i+",null,0,"
					+ ""+i+",1,"+i+",'text',100,"
					+ "0,1,0,'"+column_name_camel+"','"+column_comment+"','center',"
					+ ""+i+",null,null,0,1,"
					+ "'"+column_comment+"',"+i+",1);";
			insertFieldsAspectSqlBuf.append(insertFieldsAspectSql+"\r\n");
			
			insertSqls[0] = insertFieldsSqlBuf.toString();
			insertSqls[1] = insertFieldsAspectSqlBuf.toString();
		}
		return insertSqls;
	}

	/**
     * 驼峰格式字符串转换为下划线格式字符串
     * 
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     * 
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    
    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties" );
        } catch (ConfigurationException e) {
            throw new RRException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains("Entity.java.vm" )) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("Dao.java.vm" )) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if (template.contains("Service.java.vm" )) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm" )) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm" )) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Dao.xml.vm" )) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Dao.xml";
        }

        if (template.contains("menu.sql.vm" )) {
            return className.toLowerCase() + "_menu.sql";
        }

        if (template.contains("index.vue.vm" )) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
                    File.separator + moduleName + File.separator + className.toLowerCase() + ".vue";
        }

        if (template.contains("add-or-update.vue.vm" )) {
            return "main" + File.separator + "resources" + File.separator + "src" + File.separator + "views" + File.separator + "modules" +
                    File.separator + moduleName + File.separator + className.toLowerCase() + "-add-or-update.vue";
        }

        return null;
    }
}
