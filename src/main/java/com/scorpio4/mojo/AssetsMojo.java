package com.scorpio4.mojo;

import com.scorpio4.deploy.Scorpio4SesameDeployer;
import com.scorpio4.oops.ConfigException;
import com.scorpio4.oops.FactException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import java.io.IOException;

/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.maven
 * @author lee
 * Date  : 16/06/2014
 * Time  : 5:38 PM
 */


/**
 * Goal that imports Assets and Scripts
 *
 * @goal Assets
 * @requiresProject true
 * @phase process-sources
 */

public class AssetsMojo extends ScorpioMojo {

    @Override
    public void executeInternal() throws FactException, ConfigException, IOException, RepositoryException {
        getLog().info("Loading Assets: "+getResourcesPath().getAbsolutePath());

	    RepositoryConnection connection1 = getFactSpace().getConnection();
	    Scorpio4SesameDeployer scorpio4SesameDeployer = new Scorpio4SesameDeployer(getIdentity(), connection1);
        scorpio4SesameDeployer.setDeployRDF(false);
        scorpio4SesameDeployer.setDeployScripts(true);

        scorpio4SesameDeployer.deploy(getResourcesPath());
    }
}
