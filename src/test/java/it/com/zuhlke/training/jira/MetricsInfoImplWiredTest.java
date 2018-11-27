package it.com.zuhlke.training.jira;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.testkit.client.Backdoor;
import com.atlassian.jira.testkit.client.JIRAEnvironmentData;
import com.atlassian.jira.testkit.client.util.TestKitLocalEnvironmentData;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserProjectHistoryManager;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.plugins.osgi.test.AtlassianPluginsTestRunner;
import com.atlassian.sal.api.ApplicationProperties;
import com.zuhlke.training.jira.api.MetricsInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(AtlassianPluginsTestRunner.class)
public class MetricsInfoImplWiredTest {

    final static String userName      = "admin";
    final static String projectName   = "SampleProject";
    final static String projectKey    = "SAM";
    final static String projectType   = "business";

    private final ApplicationProperties applicationProperties;
    private final MetricsInfo metricsInfo;
    private final Backdoor backdoor;
    private final JIRAEnvironmentData environmentData;
    private final UserManager userManager;
    private final ApplicationUser applicationUser;
    private final JiraHelper jiraHelper;
    private final UserProjectHistoryManager userProjectHistoryManager;

    public MetricsInfoImplWiredTest(ApplicationProperties applicationProperties, MetricsInfo metricsInfo) {
        this.applicationProperties = applicationProperties;
        this.metricsInfo           = metricsInfo;
        this.environmentData       = new TestKitLocalEnvironmentData(); // localtest.properties supplies defaults
        this.backdoor              = new Backdoor(environmentData);
        this.userManager           = ComponentAccessor.getUserManager();
        this.applicationUser       = userManager.getUserByName(userName);
        this.jiraHelper            = new JiraHelper();
        userProjectHistoryManager  = metricsInfo.getUserProjectHistoryManager();
    }

    @Before
    public void setup() {
        deleteSampleData();
        /* final long projectId = */ backdoor.project().addProject(projectName, projectKey, userName, projectType);
        final Project project = ComponentAccessor.getProjectManager().getProjectByCurrentKey(projectKey);
        userProjectHistoryManager.addProjectToHistory(applicationUser, project);
    }

    @After
    public void cleanup() {
        // Comment out this line if you want sample data to remain in the system after the tests have run
        deleteSampleData();
    }

    private void deleteSampleData() {
        try {
            backdoor.project().deleteProject(projectKey);
        } catch (Throwable e) {
            // ignore
        }
    }

    @Test
    public void shouldHaveCorrectInterfaces() {
        assertTrue("applicationProperties should be instance of ApplicationProperties", applicationProperties instanceof ApplicationProperties);
        assertTrue("metricsInfo should be instance of MetricsInfo", metricsInfo instanceof MetricsInfo);
    }

    @Test
    public void shouldReturnProjectLead() {
        final Map contextMap = metricsInfo.getContextMap(applicationUser, jiraHelper);
        final Project project = (Project) contextMap.get(MetricsInfo.PROJECT);
        assertNotNull("Project project should not be null", project);
        assertEquals("Project Lead Name", userName, project.getLeadUserName());
    }
}
