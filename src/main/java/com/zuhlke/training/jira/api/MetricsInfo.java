package com.zuhlke.training.jira.api;

import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserProjectHistoryManager;

import java.util.Map;

public interface MetricsInfo {
    String PROJECT = "project";

    Map getContextMap(ApplicationUser applicationUser, JiraHelper jiraHelper);
    UserProjectHistoryManager getUserProjectHistoryManager();
}
