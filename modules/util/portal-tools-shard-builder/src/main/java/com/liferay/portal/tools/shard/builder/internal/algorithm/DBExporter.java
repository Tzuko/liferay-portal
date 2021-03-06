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

package com.liferay.portal.tools.shard.builder.internal.algorithm;

import java.io.OutputStream;

import java.util.List;

/**
 * @author Manuel de la Peña
 */
public interface DBExporter {

	public List<String> getControlTableNames(String schemaName);

	public List<String> getPartitionedTableNames(String schemaName);

	public void write(
		long companyId, String tableName, OutputStream outputStream);

	public void write(String tableName, OutputStream outputStream);

}