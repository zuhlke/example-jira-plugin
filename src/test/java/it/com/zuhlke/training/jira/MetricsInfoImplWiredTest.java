package it.com.zuhlke.training.jira;

import com.atlassian.jira.project.Project;
import com.atlassian.plugins.osgi.test.AtlassianPluginsTestRunner;
import com.atlassian.sal.api.ApplicationProperties;
import com.zuhlke.training.jira.api.MetricsInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(AtlassianPluginsTestRunner.class)
public class MetricsInfoImplWiredTest {

    final static String userName      = "admin";

    private final ApplicationProperties applicationProperties;
    private final MetricsInfo metricsInfo;

    public MetricsInfoImplWiredTest(ApplicationProperties applicationProperties, MetricsInfo metricsInfo) {
        this.applicationProperties = applicationProperties;
        this.metricsInfo = metricsInfo;
    }

    @Test
    public void shouldHaveCorrectInterfaces() {
        assertTrue("applicationProperties should be instance of ApplicationProperties", applicationProperties instanceof ApplicationProperties);
        assertTrue("metricsInfo should be instance of MetricsInfo", metricsInfo instanceof MetricsInfo);
    }

    @Test
    public void shouldReturnProjectLead() {
        final Map contextMap = metricsInfo.getContextMap(null, null);
        final Project project = (Project) contextMap.get(MetricsInfo.PROJECT);
        assertNotNull("Project project should not be null", project);
        assertEquals("Project Lead Name", userName, project.getLeadUserName());
    }
}
