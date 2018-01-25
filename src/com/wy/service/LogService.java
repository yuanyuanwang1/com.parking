package com.wy.service;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wy.model.AuditLog;
import com.wy.superClass.SuperService;
import com.wy.util.SpringInit;

public class LogService extends SuperService {

	private Logger logger = Logger.getLogger(LogService.class);

	/**
	 * @param pid
	 *            获取对象的PID
	 * @return 要获取的对象
	 */
	public AuditLog get(String pid) {
		return hibernateTemplate.get(AuditLog.class, pid);
	}

	public static String log(AuditLog auditLog) {

		if (auditLog == null) {

			return null;

		}

		ApplicationContext applicationContext = SpringInit
				.getApplicationContext();

		LogService logService = (LogService) applicationContext
				.getBean("logService");

		logService.save(auditLog);

		return auditLog.getPid();

	}

}
