package ut.com.zuhlke.training.jira;

import com.atlassian.jira.junit.rules.MockitoContainer;
import com.atlassian.jira.junit.rules.MockitoMocksInContainer;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.project.MockProject;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserProjectHistoryManager;
import com.zuhlke.training.jira.api.MetricsInfo;
import com.zuhlke.training.jira.impl.MetricsInfoImpl;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Map;

import static com.atlassian.jira.security.Permissions.PROJECT_ADMIN;
import static com.zuhlke.training.jira.api.MetricsInfo.PROJECT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MetricsInfoImplTest {

    @Rule
    public MockitoContainer mockitoContainer = MockitoMocksInContainer.rule(this);

    @Mock
    private UserProjectHistoryManager userProjectHistoryManager;

    @Mock
    private ApplicationUser applicationUser;

    @Mock
    private JiraHelper jiraHelper;

    final String projectKey            = "SAM";
    final String projectName           = "SampleProject";
    final MockProject project          = new MockProject(10001L, projectKey, projectName);

    @Test
    public void shouldRetrieveContextMapForProject() {

        Mockito.when(userProjectHistoryManager.getCurrentProject(PROJECT_ADMIN, applicationUser)).thenReturn(project);
        final MetricsInfo metricsInfo = new MetricsInfoImpl(userProjectHistoryManager);
        final Map<String, Object> contextMap = metricsInfo.getContextMap(applicationUser, jiraHelper);

        assertNotNull("contextMap should not be null", contextMap);
        assertEquals("Project", project, contextMap.get(PROJECT));
        final Project contextProject = (Project) contextMap.get(PROJECT);
        assertEquals("Project Name", project.getName(), contextProject.getName());
    }
}
