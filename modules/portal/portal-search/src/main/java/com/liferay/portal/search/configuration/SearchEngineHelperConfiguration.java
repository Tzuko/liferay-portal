/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.SearchEngineHelperConfiguration",
	localization = "content/Language",
	name = "%search.engine.helper.configuration.name"
)
public interface SearchEngineHelperConfiguration {

	@Meta.AD(
		deflt = "com.liferay.portal.kernel.plugin.PluginPackage|com.liferay.portlet.asset.model.AssetEntry",
		required = false
	)
	public String[] excludedEntryClassNames();

}