package de.thb.fbi.msr.maus.todosliste.remote;

import org.apache.log4j.Logger;

import javax.ws.rs.ApplicationPath;

@ApplicationPath(value="/rest/*")
public class RemoteDataAccessRESTService extends javax.ws.rs.core.Application {

	protected static Logger logger = Logger.getLogger(RemoteDataAccessRESTService.class);
	
	public RemoteDataAccessRESTService() {
		logger.info("<Constructor>()");
	}
	
}
