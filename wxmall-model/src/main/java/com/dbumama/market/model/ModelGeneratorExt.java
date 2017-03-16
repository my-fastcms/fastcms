/**
 * Copyright (c) 2015-2016, BruceZCQ (zcq@zhucongqi.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dbumama.market.model;

import com.jfinal.plugin.activerecord.generator.ModelGenerator;
import com.jfinal.plugin.activerecord.generator.TableMeta;

/**
 * ModelGenerator Extension: generate the table name
 * @author BruceZCQ
 */
public class ModelGeneratorExt extends ModelGenerator {
	
	protected String tableTemplate = "\tpublic static final String table = \"%s\";%n";

	/**
	 * @param modelPackageName
	 * @param baseModelPackageName
	 * @param modelOutputDir
	 */
	public ModelGeneratorExt(String modelPackageName, String baseModelPackageName, String modelOutputDir) {
		super(modelPackageName, baseModelPackageName, modelOutputDir);
	}

	@Override
	protected void genDao(TableMeta tableMeta, StringBuilder ret) {
		super.genDao(tableMeta, ret);
		ret.append(String.format(tableTemplate, tableMeta.name));
	}
}
