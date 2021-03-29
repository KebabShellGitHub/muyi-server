package cn.kebabshell.muyi.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: plus
 * @description:
 * @author: KebabShell
 * @create: 2021-03-25 16:02
 **/

public class MyGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("F:\\programData\\code\\MyProject\\AllPojects\\muyi-server\\src\\main\\java")
                .setAuthor("kebabshell")
                .setOpen(false)
                .setFileOverride(true)
                .setSwagger2(true)
                .setBaseResultMap(true);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/muyi?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("yangqijg");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.kebabshell.muyi")
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("controller")
                .setEntity("common.entity");
        pc.setMapper("common.mapper");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        //
        //// 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        //// 如果模板引擎是 velocity
        //// String templatePath = "/templates/mapper.xml.vm";
        //
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return "F:\\programData\\code\\MyProject\\AllPojects\\muyi-server\\src\\main\\resources\\mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        ///*
        //cfg.setFileCreate(new IFileCreate() {
        //    @Override
        //    public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
        //        // 判断自定义文件夹是否需要创建
        //        checkDir("调用默认方法创建的目录，自定义目录用");
        //        if (fileType == FileType.MAPPER) {
        //            // 已经生成 mapper 文件判断存在，不想重新生成返回 false
        //            return !new File(filePath).exists();
        //        }
        //        // 允许生成模板文件
        //        return true;
        //    }
        //});
        //*/
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //
        //// 配置自定义输出模板
        ////指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        //// templateConfig.setEntity("templates/entity2.java");
        //// templateConfig.setService();
        //// templateConfig.setController();
        //
        templateConfig.setXml(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setService(null);
        templateConfig.setController(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setCapitalMode(true);
        // 公共父类
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        strategy.setInclude("pic_carousel");
        strategy.setControllerMappingHyphenStyle(true);
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
