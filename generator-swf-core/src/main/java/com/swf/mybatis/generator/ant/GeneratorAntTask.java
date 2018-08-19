package com.swf.mybatis.generator.ant;


import com.swf.mybatis.generator.api.MyBatisGenerator;
import com.swf.mybatis.generator.config.Configuration;
import com.swf.mybatis.generator.config.xml.ConfigurationParser;
import com.swf.mybatis.generator.exception.InvalidConfigurationException;
import com.swf.mybatis.generator.exception.XMLParserException;
import com.swf.mybatis.generator.internal.DefaultShellCallback;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.PropertySet;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static com.swf.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static com.swf.mybatis.generator.internal.util.message.Messages.getString;

public class GeneratorAntTask extends Task {

    private String configgfile;
    private boolean overwrite;
    private PropertySet propertyset;
    private boolean verbose;
    private String contextIds;
    private String fullyQualifiedTableNames;

    public GeneratorAntTask() {super();}

    @Override
    public void execute() throws BuildException {
        if (!stringHasValue(configgfile)) {
            throw new BuildException(getString("RuntimeErrir.0"));
        }

        List<String> warnings = new ArrayList<>();

        File configurationFile = new File(configgfile);
        if (!configurationFile.exists()) {
            throw new BuildException(getString("RuntimeError.1", configgfile));
        }

        Set<String> fullyQualifiedTables = new HashSet<>();
        if (stringHasValue(fullyQualifiedTableNames)) {
            StringTokenizer st = new StringTokenizer(fullyQualifiedTableNames, ",");
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    fullyQualifiedTables.add(s);
                }
            }
        }

        Set<String> contexts = new HashSet<>();
        if (stringHasValue(contextIds)) {
            StringTokenizer st = new StringTokenizer(contextIds, ",");
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    contexts.add(s);
                }
            }
        }

        try {
            Properties p = propertyset == null ? null : propertyset.getProperties();

            ConfigurationParser cp = new ConfigurationParser(p, warnings);
            Configuration config = cp.parseConfiguration(configurationFile);

            DefaultShellCallback callback = new DefaultShellCallback(overwrite);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

            myBatisGenerator.generate(new AntProgressCallback(this, verbose), contexts, fullyQualifiedTables);
        } catch (XMLParserException e) {
            for (String error : e.getErrors()) {
                log(error, Project.MSG_ERR);
            }
            throw new BuildException(e.getMessage());
        } catch (SQLException e) {
            throw new BuildException(e.getMessage());
        } catch (IOException e) {
            throw new BuildException(e.getMessage());
        } catch (InvalidConfigurationException e) {
            for (String error : e.getErrors()) {
                log(error, Project.MSG_ERR);
            }
            throw new BuildException(e.getMessage());
        } catch (InterruptedException e) {
            // ignor (will never happen with the DefaultShellCallback)
        } catch (Exception e) {
            log(e, Project.MSG_ERR);
            throw new BuildException(e.getMessage());
        }

        for (String error : warnings) {
            log(error, Project.MSG_WARN);
        }
    }

    public String getConfiggfile() {
        return configgfile;
    }

    public void setConfiggfile(String configgfile) {
        this.configgfile = configgfile;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public PropertySet createPropertyset() {
        if (propertyset == null) {
            propertyset = new PropertySet();
        }
        return propertyset;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public String getContextIds() {
        return contextIds;
    }

    public void setContextIds(String contextIds) {
        this.contextIds = contextIds;
    }

    public String getFullyQualifiedTableNames() {
        return fullyQualifiedTableNames;
    }

    public void setFullyQualifiedTableNames(String fullyQualifiedTableNames) {
        this.fullyQualifiedTableNames = fullyQualifiedTableNames;
    }
}
