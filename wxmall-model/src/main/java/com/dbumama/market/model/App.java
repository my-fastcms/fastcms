package com.dbumama.market.model;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.generator.BaseModelGenerator;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * Hello world!
 *
 */
public class App {
	public static void main( String[] args ) {
    	PropKit.use("db.properties");
    	
    	DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbc.url"), PropKit.get("jdbc.username"), PropKit.get("jdbc.password").trim());
    	druidPlugin.start();
		//添加自动绑定model与表插件
    	ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
    	activeRecordPlugin.setShowSql(false);
    	activeRecordPlugin.setContainerFactory(new CaseInsensitiveContainerFactory(true));
    	activeRecordPlugin.start();
    	
    	String rootPath = System.getProperty("user.dir");
    	
    	// base model 所使用的包名
		String baseModelPackageName = "com.dbumama.market.model.base";
		// base model 文件保存路径
		String baseModelOutputDir = rootPath + "/src/main/java/com/dbumama/market/model/base";
		
		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = "com.dbumama.market.model";
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = rootPath + "/src/main/java/com/dbumama/market/model";
    	
    	BaseModelGenerator baseGe = new BaseModelGenerator(baseModelPackageName, baseModelOutputDir);
		ModelGeneratorExt modelGe = new ModelGeneratorExt(modelPackageName, baseModelPackageName, modelOutputDir);
		Generator gernerator = new Generator(druidPlugin.getDataSource(), baseGe, modelGe);
		// 设置是否在 Model 中生成 dao 对象
		gernerator.setGenerateDaoInModel(true);
		// 设置是否生成字典文件
		gernerator.setGenerateDataDictionary(false);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
		gernerator.setRemovedTableNamePrefixes("t_");
		gernerator.addExcludedTable("activities_view");
		gernerator.generate();
    }
}
