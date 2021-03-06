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

package com.liferay.screens.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for ScreensUser. This utility wraps
 * {@link com.liferay.screens.service.impl.ScreensUserServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author José Manuel Navarro
 * @see ScreensUserService
 * @see com.liferay.screens.service.base.ScreensUserServiceBaseImpl
 * @see com.liferay.screens.service.impl.ScreensUserServiceImpl
 * @generated
 */
@ProviderType
public class ScreensUserServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.screens.service.impl.ScreensUserServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static boolean sendPasswordByEmailAddress(long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().sendPasswordByEmailAddress(companyId, emailAddress);
	}

	public static boolean sendPasswordByScreenName(long companyId,
		java.lang.String screenName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().sendPasswordByScreenName(companyId, screenName);
	}

	public static boolean sendPasswordByUserId(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().sendPasswordByUserId(userId);
	}

	public static ScreensUserService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ScreensUserService, ScreensUserService> _serviceTracker =
		ServiceTrackerFactory.open(ScreensUserService.class);
}