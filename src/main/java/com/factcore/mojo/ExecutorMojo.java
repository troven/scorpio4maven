package com.factcore.mojo;

import com.factcore.iq.exec.Executor;
import com.factcore.iq.exec.Inferring;
import com.factcore.iq.exec.Templating;
import com.factcore.iq.exec.Scripting;
import com.factcore.oops.AssetNotSupported;
import com.factcore.oops.ConfigException;
import com.factcore.oops.FactException;
import com.factcore.oops.IQException;
import com.factcore.vendor.sesame.crud.SesameCRUD;
import org.openrdf.repository.RepositoryException;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Fact:Core (c) 2014
 * Module: com.factcore.maven
 * User  : lee
 * Date  : 16/06/2014
 * Time  : 5:39 PM
 */
/**
 * Goal that runs project's default Executor
 *
 * @goal Execute
 * @requiresProject true
 * @phase process-sources
 */
public class ExecutorMojo extends BaseFactToolsMojo {

    @Override
    public void executeInternal() throws FactException, ConfigException, IOException, RepositoryException, ExecutionException, IQException, InterruptedException, AssetNotSupported {
        getLog().info("Executing ToolChain: "+getIdentity());

        Map meta = new HashMap();
        Map it = new HashMap();
        meta.put("it", it);
        it.put("this", getIdentity());

        Executor executor = new Executor(getFactSpace(), assetRegister);

        executor.addExecutable(new Inferring(getFactSpace()));
        executor.addExecutable(new Scripting());
        executor.addExecutable(new Templating());

        SesameCRUD sesameCRUD = new SesameCRUD(getFactSpace());
        Collection<Map> rules = sesameCRUD.read("mojo/toolchain", meta);
        for(Map rule:rules) {
            String ruleURI = (String) rule.get("this");
            getLog().info("ToolChain: "+ruleURI);
            Map<String, Future> ran = executor.run(ruleURI, meta);
            getLog().info("Result: "+ruleURI+" empty: "+ran.isEmpty()+"  -> "+ran);
        }
    }
}
