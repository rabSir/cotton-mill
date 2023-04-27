package com.ctmill;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @Description mybatis-plus代码生成器
 * @Author Zyaire
 * @Date 2023/4/17
 */
public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cotton_mill?useSSL=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "981127Xjn";
        String author = "Zyaire";
        String outputDir = "D:\\MyProjects\\cotton-mill\\src\\main\\java";
        String basePackage = "com.ctmill";
        //模块名
        String moduleName = "gen";
        String mapperLocation = "D:\\MyProjects\\cotton-mill\\src\\main\\resources\\mapper" + moduleName;
        //表名 ,号多表
        String tableName = "cotton_mill_customer";
        String tablePrefix = "cotton_mill_";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir(outputDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(basePackage) // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .addTablePrefix(tablePrefix); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
